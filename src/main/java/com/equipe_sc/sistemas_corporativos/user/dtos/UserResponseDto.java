package com.equipe_sc.sistemas_corporativos.user.dtos;
import java.util.Set;

public record UserResponseDto(
        Integer id,
        String name,
        String phone,
        String username,
        Set<String> roles
)
{ }
