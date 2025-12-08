package com.cc6.recruiter.classes;

import com.cc6.recruiter.dtos.recruiter.RecruiterRequestDto;
import com.cc6.recruiter.dtos.recruiter.RecruiterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Gerenciamento de Recrutadores", description = "Endpoints para criar, buscar e atualizar dados de recrutadores.")
@RestController
@RequestMapping("/recruiters")
public class RecruiterController {

    @Autowired
    private RecruiterService service;

    @Operation(summary = "Lista todos os recrutadores", description = "Retorna uma lista paginada de todos os recrutadores registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de recrutadores retornada com sucesso.")
    })

    @GetMapping
    public ResponseEntity<Page<RecruiterResponseDto>> getAll(
            @Parameter(hidden = true)
            @PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable
    ){
        return ResponseEntity.ok(this.service.getAll(pageable));
    }

    @Operation(summary = "Busca recrutador por ID", description = "Retorna os detalhes de um recrutador específico utilizando seu ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recrutador encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Recrutador não encontrado.")
    })

    @GetMapping("/{id}")
    public ResponseEntity<RecruiterResponseDto> getById(
            @Parameter(description = "ID do recrutador (formato UUID).", example = "d9c8b7a6-f5e4-3210-9876-543210fedcba")
            @PathVariable UUID id)
    {
        return ResponseEntity.ok(this.service.getById(id));
    }

    @Operation(summary = "Cria um novo recrutador", description = "Registra um novo recrutador, incluindo seus dados de usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recrutador criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    })

    @PostMapping
    public ResponseEntity<RecruiterResponseDto> create(@RequestBody RecruiterRequestDto request){
        return ResponseEntity.ok(this.service.create(request));
    }

    @Operation(summary = "Atualiza um recrutador existente", description = "Atualiza os dados de um recrutador pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recrutador atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Recrutador a ser atualizado não encontrado.")
    })
    @PutMapping
    public ResponseEntity<RecruiterResponseDto> update(
            @Parameter(description = "ID do recrutador a ser atualizado (parâmetro de consulta).")
            @RequestParam UUID id,
            @RequestBody RecruiterRequestDto request
    ){
        return ResponseEntity.ok(this.service.update(id, request));
    }

}
