package com.cc6.cadidate.classes;

import com.cc6.cadidate.clients.UserClient;
import com.cc6.cadidate.dtos.candidate.CandidateRequestDto;
import com.cc6.cadidate.dtos.candidate.CandidateResponseDto;
import com.cc6.cadidate.dtos.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CandidateService {

    @Autowired
    private CandidateRepository repository;

    @Autowired
    private CandidateMapper mapper;

    @Autowired
    private UserClient userClient;

    private final Logger logger = LoggerFactory.getLogger(CandidateService.class);

    public Candidate findById(UUID id){
        this.logger.info("[CandidateService] Finding recruiter by id: {}", id);
        return this.repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<CandidateResponseDto> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(this.mapper::map);
    }

    public CandidateResponseDto getById(UUID id) {
        this.logger.info("[CandidateService] Getting recruiter by id: {}", id);
        Candidate candidate = this.findById(id);
        this.logger.info("[CandidateService] Found recruiter by id: {}", candidate);

        this.logger.info("[CandidateService] Getting user by id: {}", candidate.getUserId());
        User user = this.userClient.getUserById(candidate.getUserId());
        this.logger.info("[CandidateService] Found user: {}", user);
        candidate.setUser(user);
        return this.mapper.map(candidate);
    }

    public CandidateResponseDto create(CandidateRequestDto request) {
        this.logger.info("[CandidateService] Creating recruiter: {}", request);

        User user;

        if(request.userId() != null) {
            this.logger.info("[CandidateService] Fetching existing user with id: {}", request.userId());
            user = this.userClient.getUserById(request.userId());
            this.logger.info("[CandidateService] Fetched user: {}", user);
        } else if(request.user() != null) {
            this.logger.info("[CandidateService] Creating new user for recruiter");
            user = this.userClient.createUser(request.user());
            this.logger.info("[CandidateService] Created user: {}", user);
        } else {
            throw new IllegalArgumentException("Either userId or user details must be provided");
        }

        Candidate candidate = this.mapper.map(request);
        candidate.setUser(user);
        candidate.setId(user.id());
        candidate.setUserId(user.id());
        candidate.setCurriculumUrl("https://example.com/curriculum/" + user.id());
        Candidate savedCandidate = this.repository.saveAndFlush(candidate);
        this.logger.info("[CandidateService] Created recruiter: {}", savedCandidate);
        return this.mapper.map(savedCandidate);
    }

    public CandidateResponseDto update(UUID id, CandidateRequestDto request) {
        Candidate candidate = this.findById(id);

        if(request.user() != null) {
            this.logger.info("[CandidateService] Updating user for recruiter id: {}", id);
            User updatedUser = this.userClient.updateUser(candidate.getUserId(), request.user());
            this.logger.info("[CandidateService] Updated user: {}", updatedUser);
            candidate.setUser(updatedUser);
        }

        Candidate updatedCandidate = this.repository.saveAndFlush(candidate);
        return this.mapper.map(updatedCandidate);
    }

}
