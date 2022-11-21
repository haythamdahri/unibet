package com.kindredgroup.unibetlivetest.service.impl;

import com.kindredgroup.unibetlivetest.dto.SelectionDTO;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.mappers.SelectionMapper;
import com.kindredgroup.unibetlivetest.repository.SelectionRepository;
import com.kindredgroup.unibetlivetest.service.EventService;
import com.kindredgroup.unibetlivetest.service.SelectionService;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import com.kindredgroup.unibetlivetest.utils.Helpers;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
@Log4j2
public class SelectionServiceImpl implements SelectionService {

    private final SelectionRepository selectionRepository;
    private final EventService eventService;
    private final SelectionMapper selectionMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Long updateOddsRandomly() {
        final List<Selection> selectionsOpened = selectionRepository.getSelectionByStateEquals(SelectionState.OPENED);
        return selectionsOpened.isEmpty() ? 0 : selectionsOpened
                .stream()
                .map(selection -> selection.setCurrentOdd(Helpers.updateOddRandomly(selection.getCurrentOdd())))
                .map(selectionRepository::save)
                .count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long closeOddsRandomly() {
        final List<Selection> selectionsOpened = selectionRepository.getSelectionByStateEquals(SelectionState.OPENED);
        return selectionsOpened.isEmpty() ? 0 : IntStream
                .range(0, 5)
                .mapToObj(i -> selectionRepository.save(
                        selectionsOpened.get(Helpers.getRandomIndex(0, selectionsOpened.size()))
                                .setState(SelectionState.CLOSED)
                                .setResult(Helpers.setResultRandomly())))
                .count();
    }

    @Override
    public List<SelectionDTO> getEventSelections(final Long eventId, final SelectionState state) {
        if (!eventService.checkEventExists(eventId)) {
            final String message = String.format("No event found with id %d", eventId);
            log.error(message);
            throw new CustomException(ExceptionType.EVENT_NOT_FOUND, message);
        }
        if (state != null) {
            return selectionRepository
                    .findSelectionsRelatedToEventAndState(eventId, state)
                    .stream()
                    .map(selectionMapper::toDto)
                    .toList();
        }
        return selectionRepository
                .findSelectionsRelatedToEvent(eventId)
                .stream()
                .map(selectionMapper::toDto)
                .toList();
    }

    @Override
    public SelectionDTO getSelection(final Long id) {
        log.info("Find selection by id: {}", id);
        return selectionRepository
                .findById(id)
                .map(selectionMapper::toDto)
                .orElseThrow(
                        () -> new CustomException(
                                ExceptionType.SELECTION_NOT_FOUND,
                                String.format("No selection found with ID %d", id)
                        )
                );
    }

    @Override
    public Selection getSelectionEntity(final Long id) {
        log.info("Find selection entity by id: {}", id);
        return selectionRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Selection> getSelectionsByStatePage(SelectionState selectionState, Pageable pageable) {
        return selectionRepository.getSelectionByStateEqualsAndProcessedIsFalse(selectionState, pageable);
    }

    @Override
    public Selection save(final Selection selection) {
        return selectionRepository.save(selection);
    }

}
