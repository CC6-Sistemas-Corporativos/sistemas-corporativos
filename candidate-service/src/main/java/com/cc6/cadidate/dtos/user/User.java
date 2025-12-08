package com.cc6.cadidate.dtos.user;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Schema(description = "Modelo de dados de usuário retornado na resposta.")
public record User(
        @Schema(description = "ID do usuário.", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(description = "Nome completo.")
        String name,

        @Schema(description = "E-mail.")
        String email,

        @Schema(description = "Telefone.")
        String phone,

        @Schema(description = "CPF.")
        String cpf,

        @Schema(description = "Endereço.")
        String address,

        @Schema(description = "Data de nascimento.", type = "string", format = "date")
        LocalDate birthday,

        @Schema(description = "Nível de permissão.")
        String role,

        @Schema(description = "Data de criação.", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,

        @Schema(description = "Data da última atualização.", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) { }
