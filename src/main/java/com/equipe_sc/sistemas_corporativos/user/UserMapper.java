package com.equipe_sc.sistemas_corporativos.user;

import com.equipe_sc.sistemas_corporativos.auth.dtos.RegisterRequestDto;
import com.equipe_sc.sistemas_corporativos.role.Role;
import com.equipe_sc.sistemas_corporativos.role.RoleMapper;
import com.equipe_sc.sistemas_corporativos.user.dtos.UserResponseDto;
import com.equipe_sc.sistemas_corporativos.user.dtos.UserUpdateRequestDto;
import org.mapstruct.*;

import java.util.Set;

@Mapper(
        componentModel = "spring",
        uses = { RoleMapper.class, },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserResponseDto map(User user);

    User map(RegisterRequestDto request, String password, Set<Role> roles);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void map(UserUpdateRequestDto request, @MappingTarget User user);

}
