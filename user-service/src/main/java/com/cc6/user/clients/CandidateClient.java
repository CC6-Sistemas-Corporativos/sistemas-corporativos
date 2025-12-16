package com.cc6.user.clients;

import com.cc6.user.dtos.candidate.CandidateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class CandidateClient {

    private final String BASE_URL;

    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(CandidateClient.class);

    public CandidateClient(
            @Value("${candidate-service.url:http://localhost:8082}") String candidateServiceUrl
    ){
        this.BASE_URL = candidateServiceUrl + "/v1/candidates";
        this.restTemplate = new RestTemplate();
        this.logger.info("[CandidateClient] Initialized with BASE_URL: {}", this.BASE_URL);
    }

    public CandidateDto getCandidateById(UUID id) {
        this.logger.info("[CandidateClient] Fetching candidate by id: {}", id);
        try {
            String url = this.BASE_URL + "/" + id;
            this.logger.info("[CandidateClient] Trying to fetch candidate, url: {}", url);
            return restTemplate.getForObject(url, CandidateDto.class);

        } catch (HttpClientErrorException.NotFound e) {
            this.logger.error("[CandidateClient] Candidate not found: {}", id);
            throw new RuntimeException("Candidato não encontrado: " + id);

        } catch (HttpStatusCodeException e) {
            this.logger.error("[CandidateClient] HTTP error calling Candidate Service: {} - {}",
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Erro ao chamar Candidate Service: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            this.logger.error("[CandidateClient] Failed to connect to Candidate Service", e);
            throw new RuntimeException("Falha ao conectar ao Candidate Service", e);
        }
    }

    public void deleteCandidateById(UUID id) {
        this.logger.info("[CandidateClient] Deleting candidate by id: {}", id);
        try {
            String url = this.BASE_URL + "/" + id;
            this.logger.info("[CandidateClient] Trying to delete candidate, url: {}", url);
            restTemplate.delete(url);

        } catch (HttpClientErrorException.NotFound e) {
            this.logger.error("[CandidateClient] Candidate not found for deletion: {}", id);
            throw new RuntimeException("Candidato não encontrado para exclusão: " + id);

        } catch (HttpStatusCodeException e) {
            this.logger.error("[CandidateClient] HTTP error calling Candidate Service for deletion: {} - {}",
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Erro ao chamar Candidate Service para exclusão: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            this.logger.error("[CandidateClient] Failed to connect to Candidate Service for deletion", e);
            throw new RuntimeException("Falha ao conectar ao Candidate Service para exclusão", e);
        }
    }

}
