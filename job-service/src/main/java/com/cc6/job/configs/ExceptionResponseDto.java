package com.cc6.job.configs;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponseDto(
    String message,
    String path,
    HttpStatus status,
    LocalDateTime timestamp
) { }
