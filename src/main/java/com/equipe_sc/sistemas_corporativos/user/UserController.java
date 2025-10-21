package com.equipe_sc.sistemas_corporativos.user;

import com.equipe_sc.sistemas_corporativos.user.dtos.UserResponseDto;
import com.equipe_sc.sistemas_corporativos.user.dtos.UserSearchDto;
import com.equipe_sc.sistemas_corporativos.user.dtos.UserUpdateRequestDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService service;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Integer id) {
        this.logger.info("[UserController] - Getting user by id: {}", id);
        return ResponseEntity.ok(this.service.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> patch(
            @PathVariable Integer id,
            @RequestBody UserUpdateRequestDto request,
            @AuthenticationPrincipal User authenticatedUser
    ) {
        this.logger.info("[UserController] - Patching user by id: " + id);
        return ResponseEntity.ok(this.service.patch(id, request, authenticatedUser));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDto>> search(
            @Valid UserSearchDto request,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        this.logger.info("[UserController] - Searching users");
        return ResponseEntity.ok(this.service.search(request, pageable));
    }

}
