package com.cc6.cadidate.classes;

import com.cc6.cadidate.dtos.candidate.CandidateRequestDto;
import com.cc6.cadidate.dtos.candidate.CandidateResponseDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface CandidateMapper {

    CandidateResponseDto map(Candidate candidate);

    Candidate map(CandidateRequestDto request);

}
