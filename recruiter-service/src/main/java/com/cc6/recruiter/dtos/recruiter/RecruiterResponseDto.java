package com.cc6.recruiter.dtos.recruiter;


import com.cc6.cadidate.dtos.user.User;

import java.util.UUID;

public record RecruiterResponseDto(
        UUID id,
        UUID userId,
        User user
)
{ }
