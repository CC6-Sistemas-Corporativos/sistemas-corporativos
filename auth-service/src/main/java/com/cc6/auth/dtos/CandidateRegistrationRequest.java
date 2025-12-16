package com.cc6.auth.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record CandidateRegistrationRequest(
        // Dados do usuário
        String name,
        String email,
        String password,
        String phone,
        String cpf,
        String address,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthday
) {
}
