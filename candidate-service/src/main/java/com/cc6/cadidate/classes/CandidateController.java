package com.cc6.cadidate.classes;

import com.cc6.cadidate.dtos.candidate.CandidateRequestDto;
import com.cc6.cadidate.dtos.candidate.CandidateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Gerenciamento de Candidatos", description = "Endpoints para o CRUD de candidatos e suas informações relacionadas a usuário.")
@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService service;

    @Operation(summary = "Lista todos os candidatos", description = "Retorna uma lista paginada de todos os candidatos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de candidatos retornada com sucesso.")
    })

    @GetMapping
    public ResponseEntity<Page<CandidateResponseDto>> getAll(
            @Parameter(hidden = true)
            @PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable
    ){
        return ResponseEntity.ok(this.service.getAll(pageable));
    }

    @Operation(summary = "Busca candidato por ID", description = "Retorna os detalhes de um candidato específico utilizando seu ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado.")
    })

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDto> getById(
            @Parameter(description = "ID do candidato (formato UUID).", example = "f8a7b6c5-d4e3-21a0-9876-543210fedcba")
            @PathVariable UUID id){
        return ResponseEntity.ok(this.service.getById(id));
    }

    @Operation(summary = "Cria um novo candidato", description = "Registra um novo candidato, incluindo seus dados de usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    })

    @PostMapping
    public ResponseEntity<CandidateResponseDto> create(@RequestBody CandidateRequestDto request){
        return ResponseEntity.ok(this.service.create(request));
    }

    @Operation(summary = "Atualiza um candidato existente", description = "Atualiza os dados de um candidato pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Candidato a ser atualizado não encontrado.")
    })

    @PutMapping
    public ResponseEntity<CandidateResponseDto> update(
            @RequestParam UUID id,
            @RequestBody CandidateRequestDto request
    ){
        return ResponseEntity.ok(this.service.update(id, request));
    }

}
