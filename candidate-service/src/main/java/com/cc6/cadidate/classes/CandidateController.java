package com.cc6.cadidate.classes;

import com.cc6.cadidate.dtos.candidate.CandidateRequestDto;
import com.cc6.cadidate.dtos.candidate.CandidateResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService service;

    @GetMapping
    public ResponseEntity<Page<CandidateResponseDto>> getAll(
            @PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable
    ){
        return ResponseEntity.ok(this.service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDto> getById(@PathVariable UUID id){
        return ResponseEntity.ok(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<CandidateResponseDto> create(@RequestBody CandidateRequestDto request){
        return ResponseEntity.ok(this.service.create(request));
    }

    @PutMapping
    public ResponseEntity<CandidateResponseDto> update(
            @RequestParam UUID id,
            @RequestBody CandidateRequestDto request
    ){
        return ResponseEntity.ok(this.service.update(id, request));
    }

}
