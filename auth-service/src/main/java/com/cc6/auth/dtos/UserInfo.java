package com.cc6.auth.dtos;

import java.util.UUID;

public record UserInfo(
        UUID id,
        String name,
        String email,
        String role
) {
}
