package com.kindredgroup.unibetlivetest.exception;

import com.kindredgroup.unibetlivetest.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ExceptionHttpTranslator {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDto> businessException(
            final HttpServletRequest request, final CustomException e) {
        return ResponseEntity.status(e.getException().getStatusCode())
                .body(
                        ExceptionDto.builder()
                                .errormessage(e.getMessage())
                                .path(request.getServletPath())
                                .timestamp(LocalDateTime.now().toString())
                                .build()
                );
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            NoHandlerFoundException.class
    })
    public ResponseEntity<ExceptionDto> handleMissingServletRequestParameterException(
            final HttpServletRequest request, final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionDto.builder()
                                .errormessage(e.getMessage())
                                .path(request.getServletPath())
                                .timestamp(LocalDateTime.now().toString())
                                .build()
                );
    }

}
