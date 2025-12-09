package com.cc6.job.classes.inscriptions;

import com.cc6.job.dtos.inscriptions.InscriptionRequestDto;
import com.cc6.job.dtos.inscriptions.InscriptionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface InscriptionMapper {

    @Mapping(target = "jobId", source = "jobVacancyId")
    InscriptionResponseDto map(Inscription inscription);

    Inscription map(InscriptionRequestDto request);

}
