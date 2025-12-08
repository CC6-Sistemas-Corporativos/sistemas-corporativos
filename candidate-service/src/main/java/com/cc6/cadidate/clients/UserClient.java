package com.cc6.cadidate.clients;

import com.cc6.cadidate.dtos.user.UserRequestDto;
import com.cc6.cadidate.dtos.user.User;
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
            @Value("${gateway.url}") String gatewayUrl
    ){
        this.BASE_URL = gatewayUrl + "/users";
        this.restTemplate = new RestTemplate();
    }

    public User getUserById(UUID id) {
        this.logger.info("[UserClient] Fetching user by id: {}", id);
        try {
            String url = this.BASE_URL + "/" + id;
            this.logger.info("[UserClient] Trying to fetch user, url: {}", url);
            return restTemplate.getForObject(url, User.class);

        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Usuário não encontrado: " + id);

        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Erro ao chamar User Service: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            throw new RuntimeException("Falha ao conectar ao User Service", e);
        }
    }

    public User createUser(UserRequestDto request) {
        try {
            return restTemplate.postForObject(this.BASE_URL, request, User.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar usuário", e);
        }
    }

    public User updateUser(UUID id, UserRequestDto request) {
        try {
            String url = this.BASE_URL + "/" + id;
            restTemplate.put(url, request);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
        return null;
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
