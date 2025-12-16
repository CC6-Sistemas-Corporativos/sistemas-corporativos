package com.cc6.user.dtos.candidate;


import com.cc6.user.dtos.user.UserDto;

import java.util.UUID;

public record CandidateDto(
        UUID id,
        UUID userId,
        String curriculumUrl,
        UserDto user
) { }
