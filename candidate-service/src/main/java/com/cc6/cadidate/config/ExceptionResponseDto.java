package com.cc6.cadidate.config;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponseDto(
    String message,
    String path,
    HttpStatus status,
    LocalDateTime timestamp
) { }
