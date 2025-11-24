package com.cc6.user.dtos;

import com.cc6.user.classes.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        String password,
        String phone,
        String cpf,
        String address,
        LocalDate birthday,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
