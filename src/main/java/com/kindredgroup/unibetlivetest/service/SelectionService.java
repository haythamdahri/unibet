package com.kindredgroup.unibetlivetest.service;

import com.kindredgroup.unibetlivetest.dto.SelectionDTO;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SelectionService {

    /**
     * 1. Récupère toute les selections ouvertes
     * 2. Mis à jour la cote aléatoirement
     */
    Long updateOddsRandomly();

    /**
     * 1. Récupère toute les selections ouvertes
     * 2. Ferme 5 sélections aléatoirement.
     */
    Long closeOddsRandomly();

    /**
     * Get {@link List} of {@link SelectionDTO} based on given {@link com.kindredgroup.unibetlivetest.entity.Event} ID and state if necessary
     *
     * @param eventId given {@link com.kindredgroup.unibetlivetest.entity.Event} ID
     * @param state   given {@link SelectionState}
     * @return {@link List} of {@link SelectionDTO}
     */
    List<SelectionDTO> getEventSelections(final Long eventId, final SelectionState state);

    /**
     * Get {@link SelectionDTO} by ID
     *
     * @param id given {@link com.kindredgroup.unibetlivetest.entity.Selection} Id
     * @return found {@link SelectionDTO}
     */
    SelectionDTO getSelection(final Long id);

    /**
     * Get {@link Selection} by ID
     *
     * @param id given {@link com.kindredgroup.unibetlivetest.entity.Selection} Id
     * @return found {@link Selection}
     */
    Selection getSelectionEntity(final Long id);

    /**
     * Fetch selections by state and given page
     *
     * @param selectionState given {@link SelectionState}
     * @param pageable       given {@link Pageable}
     * @return {@link Page} of {@link Selection}
     */
    Page<Selection> getSelectionsByStatePage(final SelectionState selectionState, final Pageable pageable);

    /**
     * @param selection given {@link Selection}
     * @return saved {@link Selection}
     */
    Selection save(final Selection selection);

}
