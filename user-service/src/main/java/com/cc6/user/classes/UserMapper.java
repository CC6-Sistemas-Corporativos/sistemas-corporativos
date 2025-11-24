package com.cc6.user.classes;

import com.cc6.user.dtos.UserRequestDto;
import com.cc6.user.dtos.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User map(UserRequestDto request);

    UserResponseDto map(User user);

    @Mapping(target = "password", ignore = true)
    void map(@MappingTarget User user, UserRequestDto request);

}
