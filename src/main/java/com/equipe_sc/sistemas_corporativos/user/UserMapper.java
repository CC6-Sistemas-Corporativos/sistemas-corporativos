package com.equipe_sc.sistemas_corporativos.user;

import com.equipe_sc.sistemas_corporativos.auth.dtos.RegisterRequestDto;
import com.equipe_sc.sistemas_corporativos.role.Role;
import com.equipe_sc.sistemas_corporativos.role.RoleMapper;
import com.equipe_sc.sistemas_corporativos.user.dtos.UserResponseDto;
import com.equipe_sc.sistemas_corporativos.user.dtos.UserUpdateRequestDto;
import org.mapstruct.*;
import org.springframework.jmx.export.annotation.ManagedOperation;

import java.util.Set;

@Mapper(
        componentModel = "spring",
        uses = { RoleMapper.class, },
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapToStrings")
    UserResponseDto map(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "password", source = "password")
    User map(RegisterRequestDto request, String password);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void map(UserUpdateRequestDto request, @MappingTarget User user);

}
