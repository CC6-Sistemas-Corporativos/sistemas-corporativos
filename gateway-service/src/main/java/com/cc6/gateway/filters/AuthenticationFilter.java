package com.cc6.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final WebClient webClient;
    private final List<String> publicPaths = List.of(
            "/v1/auth/login",
            "/v1/auth/register/candidate",
            "/v1/auth/health",
            "/actuator"
    );

    private final List<String> publicMethods = List.of(
            "POST:/v1/candidates"  // Permite registro de candidatos
    );

    public AuthenticationFilter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        log.debug("Processing request to: {}", path);
        if (isPublicPath(path)) {
            log.debug("Public path accessed: {}", path);
            return chain.filter(exchange);
        }

        String method = request.getMethod().toString();
        if (isPublicMethod(method, path)) {
            log.debug("Public method accessed: {} {}", method, path);
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header for path: {}", path);
            return unauthorizedResponse(exchange);
        }

        return validateToken(authHeader)
                .flatMap(isValid -> {
                    if (isValid) {
                        log.debug("Token validated successfully for path: {}", path);
                        return chain.filter(exchange);
                    } else {
                        log.warn("Invalid token for path: {}", path);
                        return unauthorizedResponse(exchange);
                    }
                })
                .onErrorResume(error -> {
                    log.error("Error validating token for path {}: {}", path, error.getMessage());
                    return unauthorizedResponse(exchange);
                });
    }

    private boolean isPublicPath(String path) {
        return publicPaths.stream().anyMatch(path::startsWith);
    }

    private boolean isPublicMethod(String method, String path) {
        String methodPath = method + ":" + path;
        return publicMethods.stream().anyMatch(methodPath::equals);
    }

    private Mono<Boolean> validateToken(String token) {
        return webClient.get()
                .uri("http://localhost:8085/v1/auth/validate")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    Boolean valid = (Boolean) response.get("valid");
                    return valid != null && valid;
                })
                .onErrorReturn(false);
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // Execute before other filters
    }
}
