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

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(
            @PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable
    ){
        return ResponseEntity.ok(this.service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID id){
        return ResponseEntity.ok(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto request){
        return ResponseEntity.ok(this.service.create(request));
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(
            @RequestParam UUID id,
            @RequestBody UserRequestDto request
    ){
        return ResponseEntity.ok(this.service.update(id, request));
    }

}
