package com.kindredgroup.unibetlivetest.service.impl;

import com.kindredgroup.unibetlivetest.constants.EventConstants;
import com.kindredgroup.unibetlivetest.dto.EventDTO;
import com.kindredgroup.unibetlivetest.dto.SelectionDTO;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.mappers.EventMapper;
import com.kindredgroup.unibetlivetest.mappers.SelectionMapper;
import com.kindredgroup.unibetlivetest.repository.EventRepository;
import com.kindredgroup.unibetlivetest.service.EventService;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import com.kindredgroup.unibetlivetest.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class EventServiceImpl implements EventService {

    private static final Sort START_DATE_ASC_SORT = Sort.by(Sort.Direction.DESC, "startDate");

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final SelectionMapper selectionMapper;

    @Override
    public boolean checkEventExists(Long id) {
        return eventRepository.existsById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventDTO getEventById(Long id) {
        log.info("Get event by id : {}", id);
        return eventRepository
                .findById(id)
                .map(eventMapper::toDto)
                .orElseThrow(() -> new CustomException(ExceptionType.EVENT_NOT_FOUND, "No event found with id " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<EventDTO> getEventsPage(final int page, final int size, final Boolean isLive) {
        log.info("Get events page: {} size: {} isLive: {}", page, size, isLive);
        if (Boolean.TRUE.equals(isLive)) {
            return eventRepository.findEventsPageByDate(
                    DateUtils.getDateBeforeMinutes(EventConstants.EVENT_DURATION_IN_MINUTES),
                    PageRequest.of(page, size)
            ).map(this.eventMapper::toDto);
        }
        return eventRepository
                .findEventsPage(PageRequest.of(page, size, START_DATE_ASC_SORT))
                .map(this.eventMapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventDTO> getEvents(final Boolean isLive) {
        log.info("Get events isLive: {}", isLive);
        if (Boolean.TRUE.equals(isLive)) {
            return eventRepository.findEventsByDate(
                    DateUtils.getDateBeforeMinutes(EventConstants.EVENT_DURATION_IN_MINUTES)
            ).stream().map(eventMapper::toDto).toList();
        }
        return eventRepository
                .findAll()
                .stream()
                .map(eventMapper::toDto)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<SelectionDTO> getEventSelections(final Long id, final SelectionState selectionState) {
        log.info("Get event: {} selections state: {}", id, selectionState);
        if (!eventRepository.existsById(id)) {
            final String message = String.format("No event found with id %d", id);
            log.error(message);
            throw CustomException
                    .builder()
                    .exception(ExceptionType.EVENT_NOT_FOUND)
                    .message(message)
                    .build();
        }
        if (selectionState != null) {
            return eventRepository
                    .getEventSelectionsBySate(id, selectionState)
                    .stream()
                    .map(selectionMapper::toDto).toList();
        }
        return eventRepository
                .getEventSelections(id)
                .stream()
                .map(selectionMapper::toDto).toList();
    }
}
