package com.equipe_sc.sistemas_corporativos.role;

import com.equipe_sc.sistemas_corporativos.role.dtos.RoleResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final String ROLE_NOT_FOUND_MESSAGE = "Role not found";

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public Collection<Role> findAll() {
        this.logger.info("[RoleService] Fetching all roles");
        return this.repository.findAll();
    }

    public Role save(Role role) {
        return this.repository.save(role);
    }

    public Role saveAndFlush(Role role) {
        return this.repository.saveAndFlush(role);
    }

    public Role findByName(RoleName name) {
        return this.repository.findByName(name)
                .orElseThrow(() -> {
                    return new EntityNotFoundException(ROLE_NOT_FOUND_MESSAGE);
                });
    }

    public Set<Role> findRolesByNameIn(Set<RoleName> names) {
        return this.repository.findRolesByNameIn(names);
    }

    public List<RoleResponseDto> getAll() {
        return this.findAll()
                    .stream()
                    .map(role -> this.mapper.map(role))
                    .toList();
    }

    public void create(RoleName name) {
        this.logger.info("[RoleService] Creating role: {}", name);
        this.repository.saveAndFlush(new Role(name));
    }

}
