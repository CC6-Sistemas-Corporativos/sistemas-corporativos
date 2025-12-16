package com.cc6.cadidate.classes;

import com.cc6.cadidate.clients.UserClient;
import com.cc6.cadidate.dtos.candidate.CandidateRequestDto;
import com.cc6.cadidate.dtos.candidate.CandidateResponseDto;
import com.cc6.cadidate.dtos.user.UserDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        this.logger.info("[CandidateService] Getting job by id: {}", candidate.getUserId());
        UserDto userDto = this.userClient.getUserById(candidate.getUserId());
        this.logger.info("[CandidateService] Found job: {}", userDto);
        candidate.setUser(userDto);
        return this.mapper.map(candidate);
    }

    public CandidateResponseDto create(CandidateRequestDto request) {
        this.logger.info("[CandidateService] Creating recruiter: {}", request);

        UserDto userDto;

        if(request.userId() != null) {
            this.logger.info("[CandidateService] Fetching existing job with id: {}", request.userId());
            userDto = this.userClient.getUserById(request.userId());
            this.logger.info("[CandidateService] Fetched job: {}", userDto);
        } else if(request.user() != null) {
            this.logger.info("[CandidateService] Creating new job for recruiter");
            userDto = this.userClient.createUser(request.user());
            this.logger.info("[CandidateService] Created job: {}", userDto);
        } else {
            throw new IllegalArgumentException("Either userId or job details must be provided");
        }

        Candidate candidate = this.mapper.map(request);
        candidate.setUser(userDto);
        candidate.setId(userDto.id());
        candidate.setUserId(userDto.id());
        candidate.setCurriculumUrl("https://example.com/curriculum/" + userDto.id());
        Candidate savedCandidate = this.repository.saveAndFlush(candidate);
        this.logger.info("[CandidateService] Created recruiter: {}", savedCandidate);
        return this.mapper.map(savedCandidate);
    }

    public CandidateResponseDto update(UUID id, CandidateRequestDto request) {
        Candidate candidate = this.findById(id);

        if(request.user() != null) {
            this.logger.info("[CandidateService] Updating job for recruiter id: {}", id);
            UserDto updatedUserDto = this.userClient.updateUser(candidate.getUserId(), request.user());
            this.logger.info("[CandidateService] Updated job: {}", updatedUserDto);
            candidate.setUser(updatedUserDto);
        }

        Candidate updatedCandidate = this.repository.saveAndFlush(candidate);
        return this.mapper.map(updatedCandidate);
    }

    public void delete(UUID id) {
        Candidate candidate = this.findById(id);
        candidate.setDeletedAt(LocalDateTime.now());
        this.repository.saveAndFlush(candidate);
    }

}
