package com.equipe_sc.sistemas_corporativos.auth;

import com.equipe_sc.sistemas_corporativos.auth.dtos.LoginRequestDto;
import com.equipe_sc.sistemas_corporativos.auth.dtos.LoginResponseDto;
import com.equipe_sc.sistemas_corporativos.auth.dtos.RegisterRequestDto;
import com.equipe_sc.sistemas_corporativos.auth.dtos.RegisterResponseDto;
import com.equipe_sc.sistemas_corporativos.user.User;
import com.equipe_sc.sistemas_corporativos.user.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/validate")
    public ResponseEntity<String> verifiedToken() {
        return ResponseEntity.ok("Token is valid");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        this.logger.info("[AuthController] Attempting to authenticate user: {}", request.username());
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        String token = this.tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token,
                this.userService.getById(((User) auth.getPrincipal()).getId())));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@org.jetbrains.annotations.NotNull @RequestBody @Valid RegisterRequestDto request) {
        this.logger.info("[AuthController] Attempting to register user: {}", request.username());
        try{
            this.authService.loadUserByUsername(request.username());
        }catch (Exception e){
            this.logger.info("[AuthController] No existing user found with username: {}", request.username());
        }

        User user = this.authService.createUser(request);
        return ResponseEntity.ok(new RegisterResponseDto(user.getId(),this.tokenService.generateToken(user)));
    }

}
