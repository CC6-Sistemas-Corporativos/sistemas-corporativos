package com.cc6.recruiter.dtos.recruiter;

import com.cc6.cadidate.dtos.user.UserRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados para criação de um novo recrutador, incluindo informações de usuário.")
public record RecruiterRequestDto(
        @Schema(description = "ID do usuário existente (opcional na criação).", example = "00000000-0000-0000-0000-000000000001", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        UUID userId,

        @Schema(description = "Dados do usuário associado ao recrutador.")
        UserRequestDto user
)
{ }
