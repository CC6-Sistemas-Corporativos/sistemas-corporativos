package com.cc6.recruiter.dtos.recruiter;


import com.cc6.recruiter.dtos.user.UserDto;

import java.util.UUID;

public record RecruiterResponseDto(
        UUID id,
        UUID userId,
        UserDto user
)
{ }
