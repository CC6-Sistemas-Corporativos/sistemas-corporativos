package com.cc6.job.classes.jobVacancys;

import com.cc6.job.classes.inscriptions.InscriptionMapper;
import com.cc6.job.dtos.jobs.JobRequestDto;
import com.cc6.job.dtos.jobs.JobResponseDto;
import com.cc6.job.dtos.jobs.JobUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring"
)
public interface JobVacancyMapper {

    JobResponseDto map(JobVacancy jobVacancy);

    JobVacancy map(JobRequestDto jobVacancy);

    void map(@MappingTarget JobVacancy jobVacancy, JobUpdateDto request);

}
