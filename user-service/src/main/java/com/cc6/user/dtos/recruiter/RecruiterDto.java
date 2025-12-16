package com.cc6.user.dtos.recruiter;


import com.cc6.user.dtos.user.UserDto;

import java.util.UUID;

public record RecruiterDto(
        UUID id,
        UUID userId,
        UserDto user
)
{ }
