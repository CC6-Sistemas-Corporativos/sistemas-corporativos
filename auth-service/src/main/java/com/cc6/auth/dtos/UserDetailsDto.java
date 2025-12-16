package com.cc6.auth.dtos;

import java.util.UUID;

/**
 * DTO que representa os detalhes completos do usuário incluindo a senha hash
package com.cc6.auth.dtos;

import java.util.UUID;

/**
 * DTO que representa os detalhes completos do usuário incluindo a senha hash
 * Usado apenas internamente para validação de login
 */
public record UserDetailsDto(
        UUID id,
        String name,
        String email,
        String password, // Senha em hash para validação
        String role
) {
}

