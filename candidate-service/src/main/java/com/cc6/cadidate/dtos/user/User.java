package com.cc6.cadidate.dtos.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record User(
        UUID id,
        String name,
        String email,
        String phone,
        String cpf,
        String address,
        LocalDate birthday,
        String role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
