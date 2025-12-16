package com.cc6.job.dtos.candidate;

import com.cc6.job.dtos.user.UserDto;

import java.util.UUID;

public record CandidateDto(
        UUID id,
        UUID userId,
        String curriculumUrl,
        UserDto user
) { }
