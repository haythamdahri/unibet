package com.kindredgroup.unibetlivetest.types;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionType {

    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
    SELECTION_NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
    BET_CONFLICT(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value()),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, 600),
    ODD_CHANGED(HttpStatus.BAD_REQUEST, 601),
    SELECTION_CLOSED(HttpStatus.BAD_REQUEST, 602);

    @Getter
    final HttpStatus status;

    @Getter
    final int statusCode;

    ExceptionType(final HttpStatus status, final int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

}
