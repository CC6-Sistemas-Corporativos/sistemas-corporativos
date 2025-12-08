package com.cc6.cadidate.dtos.candidate;

import com.cc6.cadidate.dtos.user.UserRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados para criação de um novo candidato, incluindo informações de usuário.")
public record CandidateRequestDto(
        @Schema(description = "ID do usuário existente (se já foi criado). Opcional na criação.", example = "00000000-0000-0000-0000-000000000001", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        UUID userId,

        @Schema(description = "URL do currículo do candidato.", example = "http://link.para.curriculo.pdf", requiredMode = Schema.RequiredMode.REQUIRED)
        String curriculumUrl,

        @Schema(description = "Dados do usuário associado ao candidato (necessário se o userId não for fornecido).")
        UserRequestDto user
)
{ }
