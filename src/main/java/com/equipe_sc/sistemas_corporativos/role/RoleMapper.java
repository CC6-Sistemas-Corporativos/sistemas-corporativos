package com.equipe_sc.sistemas_corporativos.role;

import com.equipe_sc.sistemas_corporativos.role.dtos.RoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoleMapper {

    @Mapping(target = "id", source = "role.id")
    @Mapping(target = "name", expression = "java(role.getName())")
    RoleResponseDto map(Role role);

    @Named("mapToStrings")
    default Set<String> mapRolesToStrings(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
