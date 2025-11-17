package com.cc6.user.classes;

import com.cc6.user.dtos.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    UserResponseDto map(User user);

}
