package com.equipe_sc.sistemas_corporativos.auth;

import com.equipe_sc.sistemas_corporativos.auth.dtos.RegisterRequestDto;
import com.equipe_sc.sistemas_corporativos.user.User;
import com.equipe_sc.sistemas_corporativos.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public User loadUserByUsername(String username) throws EntityNotFoundException {
       return this.userService.findByUsername(username);
    }

    @Transactional
    public User createUser(@Valid RegisterRequestDto request) {
        return this.userService.create(request);
    }
}
