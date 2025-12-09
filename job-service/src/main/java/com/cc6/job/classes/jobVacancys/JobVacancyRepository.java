package com.cc6.job.classes.jobVacancys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobVacancyRepository extends
        JpaRepository<JobVacancy, UUID>, JpaSpecificationExecutor<JobVacancy>{

    @Query("""
        SELECT DISTINCT j FROM JobVacancy j
        LEFT JOIN FETCH j.inscriptions i
        WHERE j.id = :id
    """)
    Optional<JobVacancy> findByIdWithInscriptions(UUID id);

    @Query("""
        SELECT DISTINCT j FROM JobVacancy j
        LEFT JOIN FETCH j.inscriptions
    """)
    List<JobVacancy> findAllWithInscriptions();

}
