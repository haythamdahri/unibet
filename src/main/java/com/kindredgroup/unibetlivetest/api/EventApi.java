package com.kindredgroup.unibetlivetest.api;

import com.kindredgroup.unibetlivetest.dto.EventDTO;
import com.kindredgroup.unibetlivetest.dto.SelectionDTO;
import com.kindredgroup.unibetlivetest.service.EventService;
import com.kindredgroup.unibetlivetest.service.SelectionService;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@RestController
@Log4j2
@RequestMapping(Urls.BASE_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventApi {

    private final EventService eventService;
    private final SelectionService selectionService;

    @GetMapping(Urls.EVENT)
    public ResponseEntity<EventDTO> getEvent(
            @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping(Urls.PAGED_EVENTS)
    public ResponseEntity<Page<EventDTO>> getEventsPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "30") int size,
            @RequestParam(name = "isLive", required = false) Boolean isLive
    ) {
        final Page<EventDTO> eventsPage = eventService.getEventsPage(page, size, isLive);
        if (eventsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventsPage);
    }

    @GetMapping(Urls.EVENTS)
    public ResponseEntity<List<EventDTO>> getAllEvents(
            @RequestParam(name = "isLive", required = false) Boolean isLive
    ) {
        final List<EventDTO> events = eventService.getEvents(isLive);
        if (isEmpty(events)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping(Urls.SELECTIONS)
    public ResponseEntity<List<SelectionDTO>> getEventSelections(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "state", required = false) SelectionState state
    ) {
        final List<SelectionDTO> eventSelections = selectionService.getEventSelections(id, state);
        if (isEmpty(eventSelections)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventSelections);
    }


}
