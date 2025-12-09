package com.cc6.job.classes.jobVacancys;

import com.cc6.job.dtos.inscriptions.InscriptionRequestDto;
import com.cc6.job.dtos.inscriptions.InscriptionResponseDto;
import com.cc6.job.dtos.jobs.JobRequestDto;
import com.cc6.job.dtos.jobs.JobResponseDto;
import com.cc6.job.dtos.jobs.JobUpdateDto;
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

@Tag(name = "Gerenciamento de vagas", description = "Endpoints para criar, buscar e atualizar dados de vagas.")
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService service;

    // --- GET ALL --- //
    @Operation(summary = "Lista todas as vagas", description = "Retorna uma lista paginada de todas as vagas registradas, com opções de ordenação e tamanho de página.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vagas retornada com sucesso.")
    })
    @GetMapping
    public ResponseEntity<Page<JobResponseDto>> getAll(
            @Parameter(hidden = true) // Oculta a documentação técnica do Pageable, pois o Swagger já a inclui de forma amigável.
            @PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable
    ){
        return ResponseEntity.ok(this.service.getAll(pageable));
    }

    @Operation(summary = "Busca vaga por ID", description = "Retorna os detalhes de uma vaga específica utilizando seu ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDto> getById(
            @Parameter(description = "ID da vaga a ser buscada (formato UUID).", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
            @PathVariable UUID id
    ){
        return ResponseEntity.ok(this.service.getById(id));
    }

    // --- CREATE ---
    @Operation(summary = "Cria uma nova vaga", description = "Registra uma nova vaga no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga criada com sucesso e retornado."),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    })
    @PostMapping
    public ResponseEntity<JobResponseDto> create(@RequestBody JobRequestDto request){
        return ResponseEntity.ok(this.service.create(request));
    }

    // --- UPDATE ---
    @Operation(summary = "Atualiza uma vaga existente", description = "Atualiza os dados de um vaga pelo seu ID, enviando um corpo de requisição com os novos dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Vaga a ser atualizada não encontrado."),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDto> update(
            @Parameter(description = "ID da vaga a ser atualizado (passado como parâmetro de consulta).", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
            @PathVariable UUID id,
            @RequestBody JobUpdateDto request
    ){
        return ResponseEntity.ok(this.service.update(id, request));
    }

    @Operation(summary = "Busca inscrições pelo ID da vaga", description = "Retorna as inscrições de uma vaga específica utilizando ID único da vaga.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga e inscrições encontradas com sucesso."),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada.")
    })
    @GetMapping("/{id}/inscriptions")
    public ResponseEntity<Page<InscriptionResponseDto>> getInscriptionsById(
            @Parameter(
                    hidden = true,
                    description = "ID da vaga para buscar as inscrições (formato UUID).",
                    example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
            )
            @PathVariable UUID id,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return ResponseEntity.ok(this.service.getInscriptionsByJobId(pageable, id));
    }

    @Operation(summary = "Busca pelo ID da inscrição", description = "Retorna a inscrição utilizando ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada.")
    })
    @GetMapping("/inscriptions/{id}")
    public ResponseEntity<InscriptionResponseDto> getInscriptionById(
            @Parameter(
                    hidden = true,
                    description = "ID da inscrição (formato UUID).",
                    example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
            )
            @PathVariable UUID id
    ){
        return ResponseEntity.ok(this.service.getInscriptionById(id));
    }

    @Operation(summary = "Cria uma nova inscrição para uma vaga", description = "Registra uma nova inscrição no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga criada com sucesso e retornado."),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    })
    @PostMapping("/{id}/inscriptions")
    public ResponseEntity<InscriptionResponseDto> createInscription(
            @PathVariable UUID id,
            @RequestBody InscriptionRequestDto request
    ){
        return ResponseEntity.ok(this.service.createInscription(id, request));
    }

}
