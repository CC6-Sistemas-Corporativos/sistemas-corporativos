package com.cc6.recruiter.classes;

import com.cc6.recruiter.dtos.recruiter.RecruiterRequestDto;
import com.cc6.recruiter.dtos.recruiter.RecruiterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {

    @Autowired
    private RecruiterService service;

    @GetMapping
    public ResponseEntity<Page<RecruiterResponseDto>> getAll(
            @PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable
    ){
        return ResponseEntity.ok(this.service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruiterResponseDto> getById(@PathVariable UUID id){
        return ResponseEntity.ok(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<RecruiterResponseDto> create(@RequestBody RecruiterRequestDto request){
        return ResponseEntity.ok(this.service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecruiterResponseDto> update(
            @PathVariable UUID id,
            @RequestBody RecruiterRequestDto request
    ){
        return ResponseEntity.ok(this.service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
