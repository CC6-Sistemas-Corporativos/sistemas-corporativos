package com.cc6.recruiter.clients;

import com.cc6.cadidate.dtos.user.UserRequestDto;
import com.cc6.recruiter.dtos.user.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class UserClient {

    private final String BASE_URL;

    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(UserClient.class);

    public UserClient(
            @Value("${user-service.url:http://localhost:8081}") String userServiceUrl
    ){
        this.BASE_URL = userServiceUrl + "/v1/users";
        this.restTemplate = new RestTemplate();
        this.logger.info("[UserClient] Initialized with BASE_URL: {}", this.BASE_URL);
    }

    public UserDto getUserById(UUID id) {
        this.logger.info("[UserClient] Fetching user by id: {}", id);
        try {
            String url = this.BASE_URL + "/" + id;
            this.logger.info("[UserClient] Trying to fetch user, url: {}", url);
            return restTemplate.getForObject(url, UserDto.class);

        } catch (HttpClientErrorException.NotFound e) {
            this.logger.error("[UserClient] User not found: {}", id);
            throw new RuntimeException("Usuário não encontrado: " + id);

        } catch (HttpStatusCodeException e) {
            this.logger.error("[UserClient] HTTP error calling User Service: {} - {}",
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Erro ao chamar User Service: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            this.logger.error("[UserClient] Failed to connect to User Service", e);
            throw new RuntimeException("Falha ao conectar ao User Service", e);
        }
    }

    public UserDto createUser(UserRequestDto request) {
        this.logger.info("[UserClient] Creating user: {}", request);
        try {
            UserDto created = restTemplate.postForObject(this.BASE_URL, request, UserDto.class);
            this.logger.info("[UserClient] User created successfully: {}", created);
            return created;
        } catch (HttpStatusCodeException e) {
            this.logger.error("[UserClient] HTTP error creating user: {} - {}",
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Erro ao criar usuário: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            this.logger.error("[UserClient] Failed to create user", e);
            throw new RuntimeException("Erro ao criar usuário", e);
        }
    }

    public UserDto updateUser(UUID id, UserRequestDto request) {
        this.logger.info("[UserClient] Updating user: {}", id);
        try {
            String url = this.BASE_URL + "/" + id;
            restTemplate.put(url, request);
            // Busca o usuário atualizado para retornar
            return getUserById(id);
        } catch (HttpStatusCodeException e) {
            this.logger.error("[UserClient] HTTP error updating user: {} - {}",
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            this.logger.error("[UserClient] Failed to update user", e);
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    public void deleteUser(UUID id) {
        try {
            String url = this.BASE_URL + "/" + id;
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }

}
