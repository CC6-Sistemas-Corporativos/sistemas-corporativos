package com.cc6.user.clients;

import com.cc6.user.dtos.recruiter.RecruiterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class RecruiterClient {

    private final String BASE_URL;

    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(RecruiterClient.class);

    public RecruiterClient(
            @Value("${recruiter-service.url:http://localhost:8083}") String recruiterServiceUrl
    ){
        this.BASE_URL = recruiterServiceUrl + "/v1/recruiters";
        this.restTemplate = new RestTemplate();
        this.logger.info("[RecruiterClient] Initialized with BASE_URL: {}", this.BASE_URL);
    }

    public RecruiterDto getRecruiterById(UUID id) {
        this.logger.info("[RecruiterClient] Fetching recruiter by id: {}", id);
        try {
            String url = this.BASE_URL + "/" + id;
            this.logger.info("[RecruiterClient] Trying to fetch recruiter, url: {}", url);
            return restTemplate.getForObject(url, RecruiterDto.class);

        } catch (HttpClientErrorException.NotFound e) {
            this.logger.error("[RecruiterClient] Recruiter not found: {}", id);
            throw new RuntimeException("Recrutador não encontrado: " + id);

        } catch (HttpStatusCodeException e) {
            this.logger.error("[RecruiterClient] HTTP error calling Recruiter Service: {} - {}",
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Erro ao chamar Recruiter Service: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            this.logger.error("[RecruiterClient] Failed to connect to Recruiter Service", e);
            throw new RuntimeException("Falha ao conectar ao Recruiter Service", e);
        }
    }

    public void deleteRecruiterById(UUID id) {
        this.logger.info("[RecruiterClient] Deleting recruiter by id: {}", id);
        try {
            String url = this.BASE_URL + "/" + id;
            this.logger.info("[RecruiterClient] Trying to delete recruiter, url: {}", url);
            restTemplate.delete(url);

        } catch (HttpClientErrorException.NotFound e) {
            this.logger.error("[RecruiterClient] Recruiter not found for deletion: {}", id);
            throw new RuntimeException("Recrutador não encontrado para exclusão: " + id);

        } catch (HttpStatusCodeException e) {
            this.logger.error("[RecruiterClient] HTTP error calling Recruiter Service for deletion: {} - {}",
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Erro ao chamar Recruiter Service para exclusão: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            this.logger.error("[RecruiterClient] Failed to connect to Recruiter Service for deletion", e);
            throw new RuntimeException("Falha ao conectar ao Recruiter Service para exclusão", e);
        }
    }
}
