package com.cc6.auth.dtos;

public record CandidateRegistrationResponse(
        String message,
        UserInfo user,
        String token,
        String type,
        Long expiresIn
) {
}
