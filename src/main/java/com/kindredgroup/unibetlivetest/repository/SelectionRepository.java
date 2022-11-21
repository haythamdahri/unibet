package com.kindredgroup.unibetlivetest.repository;

import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface SelectionRepository extends JpaRepository<Selection, Long> {

    List<Selection> getSelectionByStateEquals(final SelectionState state);

    @EntityGraph(value = "selection-entity-graph-with-bets", type = EntityGraph.EntityGraphType.LOAD)
    Page<Selection> getSelectionByStateEqualsAndProcessedIsFalse(final SelectionState state, @PageableDefault final Pageable pageable);

    @Query("SELECT s FROM Selection s WHERE s.market.event.id = :eventId")
    List<Selection> findSelectionsRelatedToEvent(@Param("eventId") final Long eventId);

    @Query("SELECT s FROM Selection s WHERE s.market.event.id = :eventId AND s.state = :state")
    List<Selection> findSelectionsRelatedToEventAndState(
            @Param("eventId") final Long eventId, @Param("state") final SelectionState state
    );

}
