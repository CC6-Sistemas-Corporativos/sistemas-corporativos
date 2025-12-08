package com.cc6.recruiter.dtos.recruiter;


import com.cc6.cadidate.dtos.user.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta de um recrutador.")
public record RecruiterResponseDto(
        @Schema(description = "ID do recrutador.", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(description = "ID do usuário associado.", accessMode = Schema.AccessMode.READ_ONLY)
        UUID userId,

        @Schema(description = "Detalhes completos do usuário associado.")
        User user
)
{ }
