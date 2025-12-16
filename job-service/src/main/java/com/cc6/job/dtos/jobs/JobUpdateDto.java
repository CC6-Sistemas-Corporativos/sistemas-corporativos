package com.cc6.job.dtos.jobs;

import com.cc6.job.dtos.inscriptions.InscriptionResponseDto;

import java.util.Set;
import java.util.UUID;

public record JobUpdateDto(
    String title,
    String description
)
{ }
