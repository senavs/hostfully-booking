package com.senavs.booking.controller;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ErrorHandlingController {

    public static final String REQUEST_VALIDATION_ERROR = "Request Validation Error";
    public static final String INVALID_REQUEST = "Invalid Request";

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage handleValidationError(final MethodArgumentNotValidException exception) {
        final List<String> errors = exception.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> (FieldError) objectError)
                .map(fieldError -> String.format("[%s] %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return ErrorMessage.builder()
                .message(REQUEST_VALIDATION_ERROR)
                .errors(errors)
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    public ErrorMessage handleInvalidParameterException(final InvalidParameterException exception) {
        return ErrorMessage.builder()
                .message(INVALID_REQUEST)
                .errors(List.of(exception.getMessage()))
                .build();
    }


    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorMessage handleRuntimeException(final RuntimeException exception) {
        return ErrorMessage.builder()
                .message("Internal Server Error")
                .errors(List.of())
                .build();
    }

    @Getter
    @Builder
    public static class ErrorMessage {
        private final String message;
        private final List<String> errors;
    }

}
