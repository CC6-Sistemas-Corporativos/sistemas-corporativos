package com.cc6.cadidate.dtos.candidate;

import com.cc6.cadidate.dtos.user.UserRequestDto;

import java.util.UUID;

public record CandidateRequestDto(
        UUID userId,
        String curriculumUrl,
        UserRequestDto user
)
{ }
