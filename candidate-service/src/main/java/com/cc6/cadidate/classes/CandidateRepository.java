package com.cc6.cadidate.classes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CandidateRepository extends
        JpaRepository<Candidate, UUID>, JpaSpecificationExecutor<Candidate>{

}
