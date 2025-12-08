package com.cc6.user.dtos;

import com.cc6.user.classes.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema; // Importação adicionada

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

// Define a descrição do esquema de resposta completo
@Schema(description = "Estrutura completa do usuário retornada após criação ou busca.")
public record UserResponseDto(
        @Schema(description = "ID único e imutável do usuário.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(description = "Nome completo do usuário.")
        String name,

        @Schema(description = "Endereço de e-mail do usuário.")
        String email,

        @Schema(description = "ATENÇÃO: Este campo deve ser removido ou mascarado em ambientes de produção.", accessMode = Schema.AccessMode.WRITE_ONLY)
        String password, // Recomenda-se remover o campo password de DTOs de resposta

        @Schema(description = "Número de telefone.")
        String phone,

        @Schema(description = "CPF do usuário.")
        String cpf,

        @Schema(description = "Endereço completo.")
        String address,

        @Schema(description = "Data de nascimento.", type = "string", format = "date")
        LocalDate birthday,

        @Schema(description = "Nível de permissão do usuário.")
        Role role,

        @Schema(description = "Timestamp da criação do registro.", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,

        @Schema(description = "Timestamp da última atualização do registro.", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) { }
