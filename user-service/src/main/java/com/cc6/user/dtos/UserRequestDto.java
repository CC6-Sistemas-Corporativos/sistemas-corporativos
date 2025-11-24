package com.cc6.user.dtos;

import com.cc6.user.classes.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserRequestDto(
        String name,
        String email,
        String password,
        String phone,
        String cpf,
        String address,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthday,
        Role role
) { }
