package com.equipe_sc.sistemas_corporativos.auth.dtos;

import com.equipe_sc.sistemas_corporativos.user.dtos.UserResponseDto;

public record LoginResponseDto(
        String token,
        UserResponseDto user
)
{ }
