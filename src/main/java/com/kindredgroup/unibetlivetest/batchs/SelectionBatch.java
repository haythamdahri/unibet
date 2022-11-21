package com.kindredgroup.unibetlivetest.batchs;

import com.kindredgroup.unibetlivetest.entity.Bet;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.service.BetService;
import com.kindredgroup.unibetlivetest.service.CustomerService;
import com.kindredgroup.unibetlivetest.service.SelectionService;
import com.kindredgroup.unibetlivetest.types.BetState;
import com.kindredgroup.unibetlivetest.types.SelectionResult;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.kindredgroup.unibetlivetest.utils.DateUtils.UNIBET_TIMEZONE;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.collections4.CollectionUtils.size;

@Component
@Log4j2
@RequiredArgsConstructor
public class SelectionBatch {

    private final SelectionService selectionService;
    private final CustomerService customerService;
    private final BetService betService;

    @Scheduled(fixedRate = 5000)
    public void updateOddsRandomly() {
        log.info("{} selection(s) updated randomly.", selectionService.updateOddsRandomly());
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void closeOddsRandomly() {
        log.info("{} selections(s) closed randomly.", selectionService.closeOddsRandomly());
    }

    /**
     * Process closed selections in order to pay winner
     * This batch is resilient regarding each bet, no bet process error should impact other lines from being treated
     */
    @Scheduled(fixedRate = 1000 * 90)
    public void payBets() {
        final LocalDateTime startDate = LocalDateTime.now(UNIBET_TIMEZONE);
        log.info("Start bets payment at {}", startDate);
        final List<Selection> selections = fetchClosedSelections();
        log.info("Found {} selections to process", size(selections));
        emptyIfNull(selections).forEach(this::processSelection);
        final LocalDateTime endDate = LocalDateTime.now(UNIBET_TIMEZONE);
        final String takenTime = DurationFormatUtils.formatDuration(
                Duration.between(startDate, endDate).toMillis(), "HH:mm:ss", true
        );
        log.info("Bets processing finished at {}, took {}", endDate, takenTime);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    public void processSelection(final Selection selection) {
        final List<Bet> bets = selection.getBets();
        final SelectionResult selectionResult = selection.getResult();
        emptyIfNull(bets)
                .forEach(bet -> {
                    try {
                        final BetState betState = mapSelectionResultToBetState(selectionResult);
                        if (betState == BetState.WON) {
                            processCustomerPayment(selection, bet);
                        }
                        bet.setBetState(betState);
                        bet = betService.saveBet(bet);
                        log.info("Bet {} has been saved successfully", bet.getId());
                    } catch (Exception ex) {
                        log.error("Could not save bet {}, error {}", bet, ex);
                    }
                });
        selection.setProcessed(true);
        selectionService.save(selection);
    }

    public List<Selection> fetchClosedSelections() {
        final List<Selection> selections = new ArrayList<>();
        Pageable pageRequest = PageRequest.of(0, 50);
        Page<Selection> currentPage;
        do {
            currentPage = selectionService.getSelectionsByStatePage(
                    SelectionState.CLOSED, pageRequest
            );
            if (currentPage.hasContent()) {
                selections.addAll(currentPage.getContent());
            }
            pageRequest = currentPage.nextPageable();
        } while (currentPage.hasNext());
        return selections;
    }

    private void processCustomerPayment(final Selection selection, final Bet bet) {
        Customer customer = bet.getCustomer();
        customer.setBalance(
                customer.getBalance().add(selection.getCurrentOdd())
        );
        customer = this.customerService.saveCustomer(customer);
        bet.setCustomer(customer);
    }

    private BetState mapSelectionResultToBetState(final SelectionResult selectionResult) {
        return switch (selectionResult) {
            case WON -> BetState.WON;
            case LOST -> BetState.LOST;
        };
    }

}
