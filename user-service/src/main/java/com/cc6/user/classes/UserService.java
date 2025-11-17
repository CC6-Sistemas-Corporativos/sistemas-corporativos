package com.cc6.user.classes;

import com.cc6.user.dtos.UserResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public User findById(UUID id){
        return this.repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<UserResponseDto> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(this.mapper::map);
    }

    public UserResponseDto getById(UUID id) {
        return this.mapper.map(this.findById(id));
    }

}
