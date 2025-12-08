package com.cc6.recruiter.dtos.recruiter;

import com.cc6.cadidate.dtos.user.UserRequestDto;

import java.util.UUID;

public record RecruiterRequestDto(
        UUID userId,
        UserRequestDto user
)
{ }
