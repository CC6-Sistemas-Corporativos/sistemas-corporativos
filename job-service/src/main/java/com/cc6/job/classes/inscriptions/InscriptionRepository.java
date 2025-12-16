package com.cc6.job.classes.inscriptions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InscriptionRepository extends
        JpaRepository<Inscription, UUID>, JpaSpecificationExecutor<Inscription>{

    Page<Inscription> findByJobVacancy_Id(Pageable pageable, UUID jobId);

    boolean existsByCandidateIdAndJobVacancy_Id(UUID candidateId, UUID jobVacancyId);
    
}
