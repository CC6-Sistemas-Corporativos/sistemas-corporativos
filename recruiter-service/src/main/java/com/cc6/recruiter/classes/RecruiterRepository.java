package com.cc6.recruiter.classes;

import com.cc6.recruiter.classes.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecruiterRepository extends
        JpaRepository<Recruiter, UUID>, JpaSpecificationExecutor<Recruiter>{

}
