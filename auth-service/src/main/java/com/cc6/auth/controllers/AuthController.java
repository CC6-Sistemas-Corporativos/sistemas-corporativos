package com.cc6.auth.controllers;

import com.cc6.auth.dtos.*;
import com.cc6.auth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Endpoints para autenticação centralizada")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login de usuário",
               description = "Autentica usuário e retorna token JWT com informações do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            log.info("Tentativa de login para: {}", request.email());
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro no login: {}", e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "Validar token JWT",
               description = "Valida um token JWT e retorna informações do usuário se válido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token validado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Token inválido ou malformado")
    })
    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestBody TokenValidationRequest request) {
        try {
            log.debug("Validando token JWT");
            TokenValidationResponse response = authService.validateToken(request.token());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro na validação do token: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                new TokenValidationResponse(false, null, null, null)
            );
        }
    }

    @Operation(summary = "Validar token via Header",
               description = "Valida um token JWT enviado via Authorization header")
    @GetMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateTokenFromHeader(
            @RequestHeader("Authorization") String authHeader) {
        try {
            log.debug("Validando token do header Authorization");
            TokenValidationResponse response = authService.validateToken(authHeader);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro na validação do token do header: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                new TokenValidationResponse(false, null, null, null)
            );
        }
    }

    @Operation(summary = "Health Check", description = "Verificar se o serviço de autenticação está funcionando")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth Service is running");
    }

    @Operation(summary = "Registrar candidato",
               description = "Registra um novo candidato no sistema e retorna token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Candidato registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/register/candidate")
    public ResponseEntity<CandidateRegistrationResponse> registerCandidate(@RequestBody CandidateRegistrationRequest request) {
        try {
            log.info("Tentativa de registro de candidato para: {}", request.email());
            CandidateRegistrationResponse response = authService.registerCandidate(request);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            log.error("Erro no registro de candidato: {}", e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }
}
