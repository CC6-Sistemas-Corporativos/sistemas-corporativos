package com.equipe_sc.sistemas_corporativos.user.dtos;

import com.equipe_sc.sistemas_corporativos.role.RoleName;
import java.util.Set;

public record UserUpdateRequestDto(
        Integer id,
        String name,
        String phone,
        String username,
        String password,
        Set<RoleName> roles
)
{ }
