package com.cc6.cadidate.dtos.candidate;


import com.cc6.cadidate.dtos.user.User;

import java.util.UUID;

public record CandidateResponseDto(
        UUID id,
        UUID userId,
        String curriculumUrl,
        User user
)
{ }
