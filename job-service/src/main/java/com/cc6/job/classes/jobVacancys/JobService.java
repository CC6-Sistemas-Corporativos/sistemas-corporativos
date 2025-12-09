package com.cc6.job.classes.jobVacancys;

import com.cc6.job.classes.inscriptions.Inscription;
import com.cc6.job.classes.inscriptions.InscriptionService;
import com.cc6.job.clients.CandidateClient;
import com.cc6.job.clients.RecruiterClient;
import com.cc6.job.dtos.candidate.CandidateDto;
import com.cc6.job.dtos.inscriptions.InscriptionRequestDto;
import com.cc6.job.dtos.inscriptions.InscriptionResponseDto;
import com.cc6.job.dtos.jobs.JobRequestDto;
import com.cc6.job.dtos.jobs.JobResponseDto;
import com.cc6.job.dtos.jobs.JobUpdateDto;
import com.cc6.job.dtos.recruiter.RecruiterDto;
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
public class JobService {

    @Autowired
    private JobVacancyRepository repository;

    @Autowired
    private JobVacancyMapper mapper;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private CandidateClient candidateClient;

    @Autowired
    private RecruiterClient recruiterClient;

    private final Logger logger = LoggerFactory.getLogger(JobService.class);

    public JobVacancy findById(UUID id){
        return this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job vacancy not found"));
    }

    @Transactional(readOnly = true)
    public JobVacancy findByIdWithInscriptions(UUID id) {
        return repository.findByIdWithInscriptions(id)
                .orElseThrow(() -> new EntityNotFoundException("Job vacancy not found"));
    }

    @Transactional(readOnly = true)
    public Page<JobResponseDto> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(job -> {
            // Força o carregamento das inscrições dentro do contexto transacional
            // chamando um método que acessa a coleção lazy
            if (job.getInscriptions() != null) {
                job.getInscriptions().size(); // Inicializa a coleção lazy
            }
            return this.mapper.map(job);
        });
    }

    @Transactional(readOnly = true)
    public JobResponseDto getById(UUID id) {
        this.logger.info("[JobService] Getting job vacancy by id: {}", id);
        return this.mapper.map(this.findById(id));
    }

    // region Jobs

    @Transactional
    public JobResponseDto create(JobRequestDto request) {
        this.logger.info("[JobService] Creating job vacancy with title: {}", request.title());

        RecruiterDto recruiter = this.recruiterClient.getRecruiterById(request.recruiterId());

        if (recruiter == null) {
            this.logger.error("[JobService] Recruiter with ID {} not found.", request.recruiterId());
            throw new EntityNotFoundException("Recruiter not found");
        }

        JobVacancy jobVacancy = this.mapper.map(request);

        JobVacancy savedUser = this.repository.saveAndFlush(jobVacancy);
        return this.mapper.map(savedUser);
    }

    @Transactional
    public JobResponseDto update(UUID id, JobUpdateDto request) {
        JobVacancy jobVacancy = this.findById(id);
        this.mapper.map(jobVacancy, request);
        JobVacancy updatedUser = this.repository.saveAndFlush(jobVacancy);
        return this.mapper.map(updatedUser);
    }

    // endregion

    // region Inscriptions

    public InscriptionResponseDto getInscriptionById(UUID inscriptionId) {
        this.logger.info("[JobService] Fetching inscription with ID: {}", inscriptionId);
        return this.inscriptionService.getById(inscriptionId);
    }

    public Page<InscriptionResponseDto> getInscriptionsByJobId(Pageable pageable, UUID id) {
        this.logger.info("[JobService] Fetching inscriptions for job vacancy ID: {}", id);
        return this.inscriptionService.getByJobId(pageable, id);
    }

    @Transactional
    public InscriptionResponseDto createInscription(UUID id, InscriptionRequestDto request) {
        this.logger.info("[JobService] Creating inscription for job vacancy ID: {} and candidate ID: {}", id, request.candidateId());
        JobVacancy jobVacancy = this.findById(id);

        CandidateDto candidateDto = this.candidateClient.getCandidateById(request.candidateId());
        
        if (candidateDto == null || candidateDto.id() == null) {
            this.logger.error("[JobService] Candidate with ID {} not found.", request.candidateId());
            throw new EntityNotFoundException("Candidate not found");
        }

        this.logger.info("[JobService] Current inscriptions count: {}", jobVacancy.getInscriptions().size());

        if (this.inscriptionService.existsByCandidateIdAndJobVacancyId(
                candidateDto.id(), jobVacancy.getId()
        )) {
            this.logger.error("[JobService] Candidate with ID {} is already subscribed to job vacancy ID {}.", request.candidateId(), id);
            throw new IllegalArgumentException("Candidate is already subscribed to this job vacancy");
        }

        InscriptionResponseDto result = this.inscriptionService.create(jobVacancy, request);
        this.logger.info("[JobService] Inscription created successfully with ID: {}", result.id());

        return result;
    }

    // endregion

}
