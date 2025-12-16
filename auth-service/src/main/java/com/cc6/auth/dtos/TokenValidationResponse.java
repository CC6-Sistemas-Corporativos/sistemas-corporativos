package com.cc6.auth.dtos;

import java.util.UUID;

public record TokenValidationResponse(
        boolean valid,
        UUID userId,
        String email,
        String role
) {
}
