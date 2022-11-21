package com.kindredgroup.unibetlivetest.repository;

import com.kindredgroup.unibetlivetest.entity.Event;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT e FROM Event e")
    Page<Event> findEventsPage(@PageableDefault Pageable pageable);

    @Query(value = "SELECT e FROM Event e where e.startDate < :startDate")
    Page<Event> findEventsPageByDate(
            @Param("startDate") final Date startDate,
            @PageableDefault Pageable pageable
    );

    @Query(value = "SELECT e FROM Event e where e.startDate < :startDate")
    List<Event> findEventsByDate(@Param("startDate") final Date startDate);

    @Query(value = "SELECT m.selections FROM Event e INNER JOIN Market m on m.event = e WHERE e.id = :id")
    List<Selection> getEventSelections(@Param("id") Long id);

    @Query(value =
            "SELECT m.selections FROM Event e INNER JOIN Market m on m.event = e INNER JOIN Selection s ON s.market = m WHERE e.id = :id and s.state = :state"
    )
    List<Selection> getEventSelectionsBySate(@Param("id") Long id, @Param("state") SelectionState state);

}
