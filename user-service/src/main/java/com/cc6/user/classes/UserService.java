package com.cc6.user.classes;

import com.cc6.user.clients.CandidateClient;
import com.cc6.user.clients.RecruiterClient;
import com.cc6.user.dtos.UserRequestDto;
import com.cc6.user.dtos.UserResponseDto;
import com.cc6.user.dtos.candidate.CandidateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private CandidateClient candidateClient;

    @Autowired
    private RecruiterClient recruiterClient;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User findById(UUID id){
        return this.repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public User findByEmail(String email){
        return this.repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    public Page<UserResponseDto> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(this.mapper::map);
    }

    public UserResponseDto getById(UUID id) {
        return this.mapper.map(this.findById(id));
    }

    public UserResponseDto getByEmail(String email) {
        return this.mapper.map(this.findByEmail(email));
    }

    public UserResponseDto create(UserRequestDto request) {
        User user = this.mapper.map(request);

        user.setPassword(request.password());
        User savedUser = this.repository.saveAndFlush(user);
        return this.mapper.map(savedUser);
    }

    public UserResponseDto update(UUID id, UserRequestDto request) {
        User user = this.findById(id);
        this.mapper.map(user, request);
        User updatedUser = this.repository.saveAndFlush(user);
        return this.mapper.map(updatedUser);
    }

    public void delete(UUID id) {
        this.logger.info("[JobService] Deleting job vacancy with ID: {}", id);
        User user = this.findById(id);

        try {
            CandidateDto candidateDto = this.candidateClient.getCandidateById(id);
            this.logger.info("[UserService] User with ID: {} is a candidate. Proceeding to delete associated candidate record.", id);
            this.candidateClient.deleteCandidateById(id);
        } catch (Exception e){
            this.logger.info("[UserService] No associated candidate record found for user with ID: {}", id);
        }

        try {
            this.recruiterClient.getRecruiterById(id);
            this.logger.info("[UserService] User with ID: {} is a recruiter. Proceeding to delete associated recruiter record.", id);
            this.recruiterClient.deleteRecruiterById(id);
        } catch (Exception e){
            this.logger.info("[UserService] No associated recruiter record found for user with ID: {}", id);
        }

        user.setEmail("__deleted__" + user.getId() + user.getEmail());
        user.setDeletedAt(LocalDateTime.now());
        this.repository.saveAndFlush(user);
    }

}
