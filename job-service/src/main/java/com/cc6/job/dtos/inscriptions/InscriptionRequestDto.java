package com.cc6.job.dtos.inscriptions;

import com.cc6.job.classes.enums.InscriptionStatus;

import java.util.UUID;

public record InscriptionRequestDto(
        UUID candidateId
)
{ }
