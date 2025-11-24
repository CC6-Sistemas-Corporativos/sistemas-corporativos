package com.cc6.user.dtos;

import com.cc6.user.classes.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


// Define a descrição do esquema de entrada completo
@Schema(description = "Dados necessários para criar ou atualizar um usuário.")
public record UserRequestDto(
        @Schema(description = "Nome completo do usuário.", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @Schema(description = "Endereço de e-mail único do usuário.", example = "joao.silva@empresa.com", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        @Schema(description = "Senha para o primeiro acesso. Deve ser forte.", requiredMode = Schema.RequiredMode.REQUIRED)
        String password,

        @Schema(description = "Número de telefone para contato.", example = "5511987654321")
        String phone,

        @Schema(description = "CPF do usuário, usado como identificador.", example = "123.456.789-00", requiredMode = Schema.RequiredMode.REQUIRED)
        String cpf,

        @Schema(description = "Endereço completo (rua, número, cidade, estado).", example = "Rua das Flores, 100")
        String address,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Schema(description = "Data de nascimento do usuário.", example = "1990-05-15", type = "string", format = "date")
        LocalDate birthday,

        @Schema(description = "Nível de permissão do usuário.", example = "COMMON", requiredMode = Schema.RequiredMode.REQUIRED)
        Role role
) { }
