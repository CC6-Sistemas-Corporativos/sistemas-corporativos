package com.cc6.user.classes;

import com.cc6.user.dtos.UserRequestDto;
import com.cc6.user.dtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@Tag(name = "Gerenciamento de Usuários", description = "Endpoints para criar, buscar e atualizar dados de usuários.")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    // --- GET ALL --- //
    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista paginada de todos os usuários registrados, com opções de ordenação e tamanho de página.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso.")
    })
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(
            @Parameter(hidden = true) // Oculta a documentação técnica do Pageable, pois o Swagger já a inclui de forma amigável.
            @PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable
    ){
        return ResponseEntity.ok(this.service.getAll(pageable));
    }

    @Operation(summary = "Busca usuário por ID", description = "Retorna os detalhes de um usuário específico utilizando seu ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(
            @Parameter(description = "ID do usuário a ser buscado (formato UUID).", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
            @PathVariable UUID id
    ){
        return ResponseEntity.ok(this.service.getById(id));
    }

    // --- CREATE ---
    @Operation(summary = "Cria um novo usuário", description = "Registra um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso e retornado."),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos (Ex: email já existe).")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto request){
        return ResponseEntity.ok(this.service.create(request));
    }

    // --- UPDATE ---
    @Operation(summary = "Atualiza um usuário existente", description = "Atualiza os dados de um usuário pelo seu ID, enviando um corpo de requisição com os novos dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário a ser atualizado não encontrado."),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @Parameter(description = "ID do usuário a ser atualizado (passado como parâmetro de consulta).", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
            @PathVariable UUID id,
            @RequestBody UserRequestDto request
    ){
        return ResponseEntity.ok(this.service.update(id, request));
    }

}
