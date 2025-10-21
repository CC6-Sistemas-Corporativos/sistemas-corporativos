package com.equipe_sc.sistemas_corporativos.user.dtos;

import java.time.LocalDate;

public record UserSearchDto(
        String name,
        String phone,
        String username,
        LocalDate startDate,
        LocalDate endDate
)
{ }
