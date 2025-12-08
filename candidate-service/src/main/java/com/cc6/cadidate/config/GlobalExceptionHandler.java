package com.cc6.cadidate.config;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponseDto entityNotFoundHandler(
        EntityNotFoundException e, WebRequest request
    ) {
        return new ExceptionResponseDto(
            e.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            HttpStatus.NOT_FOUND,
            LocalDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class) // Catch-all for other exceptions
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponseDto handleGenericException(Exception e, WebRequest request) {
        return new ExceptionResponseDto(
                e.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
    }
}
