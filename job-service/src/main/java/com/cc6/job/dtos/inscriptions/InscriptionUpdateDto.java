package com.cc6.job.dtos.inscriptions;

import com.cc6.job.classes.enums.InscriptionStatus;

public record InscriptionUpdateDto(
        InscriptionStatus status
)
{ }
