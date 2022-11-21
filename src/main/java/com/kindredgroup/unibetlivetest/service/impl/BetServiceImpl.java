package com.kindredgroup.unibetlivetest.service.impl;

import com.kindredgroup.unibetlivetest.dto.BetRequest;
import com.kindredgroup.unibetlivetest.entity.Bet;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.repository.BetRepository;
import com.kindredgroup.unibetlivetest.service.BetService;
import com.kindredgroup.unibetlivetest.service.CustomerService;
import com.kindredgroup.unibetlivetest.service.SelectionService;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Log4j2
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {

    private final BetRepository betRepository;
    private final CustomerService customerService;
    private final SelectionService selectionService;

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void saveBet(final String customerPseudo, final BetRequest betRequest) {
        log.info("Save Bet request: {} customerPseudo: {}", betRequest, customerPseudo);
        Customer customer = customerService.findCustomerByPseudo(customerPseudo);
        final Selection selection = selectionService.getSelectionEntity(betRequest.getSelectionId());
        validateRequest(customerPseudo, customer, selection, betRequest);
        customer.setBalance(
                customer.getBalance().subtract(betRequest.getMise())
        );
        customer = customerService.saveCustomer(customer);
        final Bet bet = Bet.builder()
                .selection(selection)
                .date(new Date())
                .customer(customer)
                .name(1)
                .build();
        selection.setCurrentOdd(betRequest.getCote());
        betRepository.save(bet);
    }

    @Override
    public Bet saveBet(final Bet bet) {
        return betRepository.save(bet);
    }

    private void validateRequest(
            final String customerPseudo,
            final Customer customer,
            final Selection selection,
            final BetRequest betRequest
    ) {
        if (customer == null) {
            final String message = String.format("customer with pseudo %s not found", customerPseudo);
            log.error(message);
            throw new CustomException(ExceptionType.CUSTOMER_NOT_FOUND, message);
        }
        if (selection == null) {
            final String message = String.format("No selection found with ID %d", betRequest.getSelectionId());
            log.error(message);
            throw new CustomException(ExceptionType.SELECTION_NOT_FOUND, message);
        }
        if (betRepository.existsByCustomer_IdAndSelection_Id(customer.getId(), betRequest.getSelectionId())) {
            final String message = String.format(
                    "Customer %d cannot have multiple bets for same selection, selectionId: %d",
                    customer.getId(),
                    betRequest.getSelectionId()
            );
            log.error(message);
            throw new CustomException(ExceptionType.BET_CONFLICT, message);
        }
        if (betRequest.getMise().compareTo(customer.getBalance()) > 0) {
            final String message = String.format(
                    "Insufficient balance for customer %d requested amount: %.2f available balance: %.2f",
                    customer.getId(),
                    betRequest.getMise(),
                    customer.getBalance()
            );
            log.error(message);
            throw new CustomException(ExceptionType.INSUFFICIENT_BALANCE, message);
        }
        if (selection.getState() == SelectionState.CLOSED) {
            final String message = String.format("Selection %d is closed", betRequest.getSelectionId());
            log.error(message);
            throw new CustomException(ExceptionType.SELECTION_CLOSED, message);
        }
        if (betRequest.getCote().compareTo(selection.getCurrentOdd()) != 0) {
            final String message = String.format(
                    "Odd has been changed, current value %.2f requested value: %.2f",
                    betRequest.getCote(),
                    selection.getCurrentOdd()
            );
            log.error(message);
            throw new CustomException(ExceptionType.ODD_CHANGED, message);
        }
    }

}
