package com.cc6.auth.dtos;

public record LoginResponse(
        String token,
        String type,
        Long expiresIn,
        UserInfo user
) {
}
