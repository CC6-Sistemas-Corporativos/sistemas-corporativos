package com.equipe_sc.sistemas_corporativos.auth.security;

import com.equipe_sc.sistemas_corporativos.auth.TokenService;
import com.equipe_sc.sistemas_corporativos.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        this.logger.info("[SecurityFilter] - Processing request: {}", request.getRequestURI());
        String token = this.tokenService.recoverToken(request);

        if (token != null) {
            UserDetails user = null;
            this.logger.info("[SecurityFilter] - Token detected");
            String username = this.tokenService.validateToken(token);
            if (username != null) {
                user = this.userService.findByUsername(username);
            }
            if (user != null) {
                UsernamePasswordAuthenticationToken userAuthentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(userAuthentication);
            }
        }

        filterChain.doFilter(request, response);

    }
}

