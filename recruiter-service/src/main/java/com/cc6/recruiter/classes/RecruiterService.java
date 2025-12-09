package com.cc6.recruiter.classes;

import com.cc6.recruiter.clients.UserClient;
import com.cc6.recruiter.dtos.recruiter.RecruiterRequestDto;
import com.cc6.recruiter.dtos.recruiter.RecruiterResponseDto;
import com.cc6.recruiter.dtos.user.UserDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository repository;

    @Autowired
    private RecruiterMapper mapper;

    @Autowired
    private UserClient userClient;

    private final Logger logger = LoggerFactory.getLogger(RecruiterService.class);

    public Recruiter findById(UUID id){
        this.logger.info("[RecruiterService] Finding recruiter by id: {}", id);
        return this.repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<RecruiterResponseDto> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(this.mapper::map);
    }

    public RecruiterResponseDto getById(UUID id) {
        this.logger.info("[RecruiterService] Getting recruiter by id: {}", id);
        Recruiter recruiter = this.findById(id);
        this.logger.info("[RecruiterService] Found recruiter by id: {}", recruiter);

        this.logger.info("[RecruiterService] Getting job by id: {}", recruiter.getUserId());
        UserDto user = this.userClient.getUserById(recruiter.getUserId());
        this.logger.info("[RecruiterService] Found job: {}", user);
        recruiter.setUser(user);
        return this.mapper.map(recruiter);
    }

    public RecruiterResponseDto create(RecruiterRequestDto request) {
        this.logger.info("[RecruiterService] Creating recruiter: {}", request);

        UserDto user;

        if(request.userId() != null) {
            this.logger.info("[RecruiterService] Fetching existing job with id: {}", request.userId());
            user = this.userClient.getUserById(request.userId());
            this.logger.info("[RecruiterService] Fetched job: {}", user);
        } else if(request.user() != null) {
            this.logger.info("[RecruiterService] Creating new job for recruiter");
            user = this.userClient.createUser(request.user());
            this.logger.info("[RecruiterService] Created job: {}", user);
        } else {
            throw new IllegalArgumentException("Either userId or job details must be provided");
        }

        Recruiter recruiter = this.mapper.map(request);
        recruiter.setUser(user);
        recruiter.setId(user.id());
        recruiter.setUserId(user.id());
        Recruiter savedRecruiter = this.repository.saveAndFlush(recruiter);
        this.logger.info("[RecruiterService] Created recruiter: {}", savedRecruiter);
        return this.mapper.map(savedRecruiter);
    }

    public RecruiterResponseDto update(UUID id, RecruiterRequestDto request) {
        Recruiter recruiter = this.findById(id);

        if(request.user() != null) {
            this.logger.info("[RecruiterService] Updating job for recruiter id: {}", id);
            UserDto updatedUser = this.userClient.updateUser(recruiter.getUserId(), request.user());
            this.logger.info("[RecruiterService] Updated job: {}", updatedUser);
            recruiter.setUser(updatedUser);
        }

        Recruiter updatedRecruiter = this.repository.saveAndFlush(recruiter);
        return this.mapper.map(updatedRecruiter);
    }

}
