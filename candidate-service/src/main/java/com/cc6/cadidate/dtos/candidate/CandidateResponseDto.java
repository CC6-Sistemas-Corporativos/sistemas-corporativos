package com.cc6.cadidate.dtos.candidate;


import com.cc6.cadidate.dtos.user.UserDto;

import java.util.UUID;

public record CandidateResponseDto(
        UUID id,
        UUID userId,
        String curriculumUrl,
        UserDto user
)
{ }
