package com.equipe_sc.sistemas_corporativos.user;

import com.equipe_sc.sistemas_corporativos.auth.dtos.RegisterRequestDto;
import com.equipe_sc.sistemas_corporativos.role.Role;
import com.equipe_sc.sistemas_corporativos.role.RoleService;
import com.equipe_sc.sistemas_corporativos.user.dtos.UserResponseDto;
import com.equipe_sc.sistemas_corporativos.user.dtos.UserSearchDto;
import com.equipe_sc.sistemas_corporativos.user.dtos.UserUpdateRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    // region Attributes

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RoleService roleService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // endregion

    // region Methods

    // region Repository Methods

    public User findById(Integer id){
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
    }

    public User findByUsername(String username) {
        return this.repository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
    }

    public User save(User user){
        return this.repository.save(user);
    }

    public User saveAndFlush(User user) {
        return this.repository.saveAndFlush(user);
    }

    // endregion

    // region CRUD Methods

    public UserResponseDto getById(Integer id) {
        User user = this.findById(id);
        return this.mapper.map(user);
    }

    @Transactional
    public User create(@Valid RegisterRequestDto request) {
        this.logger.info("[UserService] - Creating user with username: {}", request.username());

        User user = this.mapper.map(request, this.passwordEncoder.encode(request.password()));

        Set<Role> roles = this.roleService.findRolesByNameIn(request.roles());
        this.logger.info("[UserService] - Roles found: {}", roles.size());

        if (request.roles() != null){
            if (request.roles().size() != roles.size()){
                throw new EntityNotFoundException("One or more roles not found");
            }
            user.setRoles(roles);
        } else {
          user.setRoles(roles.stream().filter(Role::isCandidate).collect(Collectors.toSet()));
        }

        User savedUser = this.repository.saveAndFlush(user);

        this.logger.info("[UserService] - User created with id: {}", savedUser.getId());
        return savedUser;
    }

    @Transactional
    public UserResponseDto patch(Integer id, UserUpdateRequestDto request, User authenticatedUser) {
        this.logger.info("[UserService] - Patching user with id: " + id);
        User user = this.findById(id);

        this.usernameCanBeAccepted(id, request.username());

        Set<Role> roles = new HashSet<>(this.roleService.findRolesByNameIn(request.roles()));
        this.mapper.map(request, user);

        if (!roles.isEmpty()){
            this.logger.info("[UserService] - Updating roles for user with id: " + id);
            user.setRoles(roles);
        }

        User savedUser = this.repository.saveAndFlush(user);
        this.logger.info("[UserService] - User with id: " + id + " patched successfully");
        return this.mapper.map(savedUser);
    }

    public Page<UserResponseDto> search(UserSearchDto request, Pageable pageable) {
        this.logger.info("[UserService] - Searching for users");
        Specification<User> spec = Specification.allOf(
                UserSpecification.byName(request.name()),
                UserSpecification.byPhone(request.phone()),
                UserSpecification.byUsername(request.username()),
                UserSpecification.afterCreatedAt(request.startDate()),
                UserSpecification.beforeCreatedAt(request.endDate())
        );
        return this.repository.findAll(spec, pageable).map(mapper::map);
    }

    // endregion

    // region Other Methods

    public void usernameCanBeAccepted(Integer id, String username) {
        Optional<User> existingUser = this.repository.findByUsername(username);
        if(existingUser.isEmpty() || existingUser.get().getId().equals(id)) return;
        throw new RuntimeException("Driver with email already exists: " + username);
    }

    // endregion

    // endregion

}
