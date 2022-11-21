package com.kindredgroup.unibetlivetest.api;

public interface Urls {

    String BASE_PATH = "/api/v1";

    /** events apis **/
    String EVENTS = "/events";
    String EVENT = EVENTS + "/{id}";
    String PAGED_EVENTS = "/paged-events";
    String SELECTIONS = EVENTS + "/{id}/selections";

    /** bets api **/
    String BETS = "bets";
    String ADD_BET = BETS + "/";


    /** customers apis **/
    String CUSTOMERS = "/customers";
    String CURRENT_CUSTOMER = CUSTOMERS + "/current";

}
