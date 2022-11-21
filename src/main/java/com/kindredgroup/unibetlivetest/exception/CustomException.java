package com.kindredgroup.unibetlivetest.exception;

import com.kindredgroup.unibetlivetest.types.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = -7542407291717358055L;

    private final ExceptionType exception;
    private final String message;

}
