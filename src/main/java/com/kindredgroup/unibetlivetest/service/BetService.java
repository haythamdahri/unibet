package com.kindredgroup.unibetlivetest.service;

import com.kindredgroup.unibetlivetest.dto.BetRequest;
import com.kindredgroup.unibetlivetest.entity.Bet;

public interface BetService {

    /**
     * Save a new bet
     *
     * @param customerPseudo given {@link com.kindredgroup.unibetlivetest.entity.Customer} Pseudo
     * @param betRequest     given {@link BetRequest}
     */
    void saveBet(final String customerPseudo, final BetRequest betRequest);

    /**
     * Save a given Bet into database
     *
     * @param bet given {@link Bet}
     * @return saved {@link Bet}
     */
    Bet saveBet(final Bet bet);

}
