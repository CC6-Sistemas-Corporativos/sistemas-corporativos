package com.equipe_sc.sistemas_corporativos.auth.dtos;

import com.equipe_sc.sistemas_corporativos.role.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record RegisterRequestDto(
        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank
        String name,

        @NotBlank
        String phone,

        @NotEmpty(message = "The user must have at least one role")
        Set<RoleName> roles
)
{ }
