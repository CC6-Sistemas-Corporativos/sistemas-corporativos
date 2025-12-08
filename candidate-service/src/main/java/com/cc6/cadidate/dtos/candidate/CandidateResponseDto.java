package com.cc6.cadidate.dtos.candidate;


import com.cc6.cadidate.dtos.user.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta de um candidato.")
public record CandidateResponseDto(
        @Schema(description = "ID do candidato.", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(description = "ID do usuário associado.", accessMode = Schema.AccessMode.READ_ONLY)
        UUID userId,

        @Schema(description = "URL do currículo.")
        String curriculumUrl,

        @Schema(description = "Detalhes completos do usuário associado.")
        User user
)
{ }
