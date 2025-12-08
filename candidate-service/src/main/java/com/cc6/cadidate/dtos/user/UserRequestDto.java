package com.cc6.cadidate.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Dados necessários para criação do usuário associado ao candidato.")
public record UserRequestDto(
        @Schema(description = "Nome completo do usuário.", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @Schema(description = "E-mail único do usuário.", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        @Schema(description = "Senha do usuário.", requiredMode = Schema.RequiredMode.REQUIRED)
        String password,

        @Schema(description = "Telefone para contato.")
        String phone,

        @Schema(description = "CPF do usuário.", requiredMode = Schema.RequiredMode.REQUIRED)
        String cpf,

        @Schema(description = "Endereço completo.")
        String address,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Schema(description = "Data de nascimento.", example = "1990-01-01", type = "string", format = "date")
        LocalDate birthday,

        @Schema(description = "Nível de permissão (role).")
        String role
) { }
