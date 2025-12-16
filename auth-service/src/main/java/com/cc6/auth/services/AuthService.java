package com.cc6.auth.services;

import com.cc6.auth.clients.UserClient;
import com.cc6.auth.clients.CandidateClient;
import com.cc6.auth.dtos.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserClient userClient;
    private final CandidateClient candidateClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.expiration}")
    private Long expiration;

    public LoginResponse login(LoginRequest request) {
        try {
            log.info("Iniciando processo de login para: {}", request.email());

            // Busca usuário por email no user-service
            UserDetailsDto userDetails = userClient.getUserByEmail(request.email());

            // Valida a senha
            if (!passwordEncoder.matches(request.password(), userDetails.password())) {
                log.warn("Senha inválida para o usuário: {}", request.email());
                throw new RuntimeException("Credenciais inválidas");
            }

            // Cria UserInfo para o response
            UserInfo userInfo = new UserInfo(
                    userDetails.id(),
                    userDetails.name(),
                    userDetails.email(),
                    userDetails.role()
            );

            // Gera token JWT
            String token = jwtService.generateToken(userInfo.id(), userInfo.email(), userInfo.role());

            log.info("Login realizado com sucesso para usuário: {}", request.email());

            return new LoginResponse(
                    token,
                    "Bearer",
                    expiration / 1000, // Convert to seconds
                    userInfo
            );

        } catch (Exception e) {
            log.error("Erro durante o login para {}: {}", request.email(), e.getMessage());
            throw new RuntimeException("Falha na autenticação: " + e.getMessage(), e);
        }
    }

    public TokenValidationResponse validateToken(String token) {
        try {
            log.debug("Validando token JWT");

            Claims claims = jwtService.validateToken(token);

            if (jwtService.isTokenExpired(claims)) {
                log.warn("Token expirado");
                return new TokenValidationResponse(false, null, null, null);
            }

            UUID userId = jwtService.getUserIdFromToken(claims);
            String email = jwtService.getEmailFromToken(claims);
            String role = jwtService.getRoleFromToken(claims);

            log.debug("Token válido para usuário: {}", email);

            return new TokenValidationResponse(true, userId, email, role);

        } catch (Exception e) {
            log.error("Erro ao validar token: {}", e.getMessage());
            return new TokenValidationResponse(false, null, null, null);
        }
    }

    public CandidateRegistrationResponse registerCandidate(CandidateRegistrationRequest request) {
        try {
            log.info("Iniciando registro de candidato para: {}", request.email());

            // Criar estrutura de dados para o candidate-service
            Map<String, Object> candidateData = new HashMap<>();

            // Dados do usuário
            Map<String, Object> userData = new HashMap<>();
            userData.put("name", request.name());
            userData.put("email", request.email());
            userData.put("password", request.password());
            userData.put("phone", request.phone());
            userData.put("cpf", request.cpf());
            userData.put("address", request.address());
            userData.put("birthday", request.birthday());
            userData.put("role", "ROLE_CANDIDATE");

            candidateData.put("user", userData);

            // Criar candidato através do candidate-service
            Map<String, Object> candidateResponse = candidateClient.createCandidate(candidateData);

            // Extrair informações do usuário criado
            @SuppressWarnings("unchecked")
            Map<String, Object> userInfo = (Map<String, Object>) candidateResponse.get("user");

            String userId = userInfo.get("id").toString();
            String email = userInfo.get("email").toString();
            String role = userInfo.get("role").toString();
            String name = userInfo.get("name").toString();

            // Gerar token JWT para o novo usuário
            String token = jwtService.generateToken(UUID.fromString(userId), email, role);

            UserInfo user = new UserInfo(
                    UUID.fromString(userId),
                    name,
                    email,
                    role
            );

            log.info("Candidato registrado com sucesso: {}", request.email());

            return new CandidateRegistrationResponse(
                    "Candidato registrado com sucesso!",
                    user,
                    token,
                    "Bearer",
                    expiration / 1000
            );

        } catch (Exception e) {
            log.error("Erro durante o registro do candidato {}: {}", request.email(), e.getMessage());
            throw new RuntimeException("Falha no registro: " + e.getMessage(), e);
        }
    }
}
