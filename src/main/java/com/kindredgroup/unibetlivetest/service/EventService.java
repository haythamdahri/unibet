package com.kindredgroup.unibetlivetest.service;

import com.kindredgroup.unibetlivetest.dto.EventDTO;
import com.kindredgroup.unibetlivetest.dto.SelectionDTO;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {

    /**
     * Check if an event exists by ID
     * @param id given {@link com.kindredgroup.unibetlivetest.entity.Event} Id
     * @return {@link EventDTO}
     */
    boolean checkEventExists(final Long id);

    /**
     * Get event by ID
     * @param id given {@link com.kindredgroup.unibetlivetest.entity.Event} Id
     * @return {@link EventDTO}
     */
    EventDTO getEventById(final Long id);

    /**
     * Get events page
     *
     * @param page   requested page number
     * @param size   request page size
     * @param isLive requested event current status (going or ended)
     * @return retrieved {@link Page} of {@link EventDTO}
     */
    Page<EventDTO> getEventsPage(final int page, final int size, final Boolean isLive);

    /**
     * Get all events
     *
     * @param isLive requested event current status (going or ended)
     * @return retrieved {@link List} of {@link EventDTO}
     */
    List<EventDTO> getEvents(final Boolean isLive);

    /**
     * Get event selections
     *
     * @param id given {@link com.kindredgroup.unibetlivetest.entity.Event} ID
     * @param selectionState given {@link SelectionState}
     * @return found {@link SelectionDTO}
     */
    List<SelectionDTO> getEventSelections(final Long id, final SelectionState selectionState);

}
