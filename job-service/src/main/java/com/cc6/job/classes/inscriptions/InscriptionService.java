package com.cc6.job.classes.inscriptions;

import com.cc6.job.classes.enums.InscriptionStatus;
import com.cc6.job.classes.jobVacancys.JobVacancy;
import com.cc6.job.dtos.inscriptions.InscriptionRequestDto;
import com.cc6.job.dtos.inscriptions.InscriptionResponseDto;
import com.cc6.job.dtos.inscriptions.InscriptionUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class InscriptionService {

    @Autowired
    private InscriptionRepository repository;

    @Autowired
    private InscriptionMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(InscriptionService.class);

    public Inscription findById(UUID id){
        return this.repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public boolean existsByCandidateIdAndJobVacancyId(UUID candidateId, UUID jobVacancyId) {
        return this.repository.existsByCandidateIdAndJobVacancy_Id(candidateId, jobVacancyId);
    }

    public Page<InscriptionResponseDto> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(this.mapper::map);
    }

    public InscriptionResponseDto getById(UUID id) {
        return this.mapper.map(this.findById(id));
    }

    @Transactional
    public InscriptionResponseDto create(JobVacancy jobVacancy, InscriptionRequestDto request) {
        this.logger.info("[InscriptionService] Creating inscription for candidate ID: {} and job ID: {}", request.candidateId(), jobVacancy.getId());
        Inscription inscription = this.mapper.map(request);
        inscription.setJobVacancy(jobVacancy);
        inscription.setStatus(InscriptionStatus.SUBSCRIBED);
        Inscription savedUser = this.repository.saveAndFlush(inscription);
        return this.mapper.map(savedUser);
    }

    public Page<InscriptionResponseDto> getByJobId(Pageable pageable, UUID id) {
        this.logger.info("[InscriptionService] Fetching inscriptions for job ID: {}", id);
        return this.repository.findByJobVacancy_Id(pageable, id).map(this.mapper::map);
    }

    public InscriptionResponseDto update(UUID id, InscriptionUpdateDto request) {
        this.logger.info("[InscriptionService] Updating inscription ID: {} with status: {}", id, request.status());
        Inscription inscription = this.findById(id);
        inscription.setStatus(request.status());
        Inscription updatedInscription = this.repository.saveAndFlush(inscription);
        this.logger.info("[InscriptionService] Inscription ID: {} updated successfully", id);
        return this.mapper.map(updatedInscription);
    }
}
