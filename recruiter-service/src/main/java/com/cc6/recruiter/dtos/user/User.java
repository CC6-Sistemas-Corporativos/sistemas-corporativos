package com.cc6.recruiter.dtos.user; // Verifique se o pacote está correto para o Recruiter Service

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Modelo de dados de usuário retornado na resposta do Recruiter Service.")
public record User(
        @Schema(description = "ID único e imutável do usuário.", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(description = "Nome completo do usuário.")
        String name,

        @Schema(description = "E-mail do usuário.")
        String email,

        @Schema(description = "Telefone para contato.")
        String phone,

        @Schema(description = "CPF do usuário.")
        String cpf,

        @Schema(description = "Endereço completo.")
        String address,

        @Schema(description = "Data de nascimento.", type = "string", format = "date", example = "1990-01-01")
        LocalDate birthday,

        @Schema(description = "Nível de permissão (role).")
        String role,

        @Schema(description = "Timestamp da criação do registro.", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,

        @Schema(description = "Timestamp da última atualização do registro.", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) { }