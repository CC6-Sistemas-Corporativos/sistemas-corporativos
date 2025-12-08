package com.cc6.recruiter.classes;

import com.cc6.recruiter.dtos.recruiter.RecruiterRequestDto;
import com.cc6.recruiter.dtos.recruiter.RecruiterResponseDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface RecruiterMapper {

    RecruiterResponseDto map(Recruiter candidate);

    Recruiter map(RecruiterRequestDto request);

}
