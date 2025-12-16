package com.cc6.auth.dtos;

public record LoginRequest(
        String email,
        String password
) {
}
