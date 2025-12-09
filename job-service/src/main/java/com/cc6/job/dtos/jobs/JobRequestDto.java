package com.cc6.job.dtos.jobs;

import java.util.UUID;

public record JobRequestDto(
    UUID recruiterId,
    String title,
    String description
)
{ }
