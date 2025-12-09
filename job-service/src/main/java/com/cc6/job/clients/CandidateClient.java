package com.cc6.job.clients;

import com.cc6.job.dtos.candidate.CandidateDto;
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
            @Value("${gateway.url}") String gatewayUrl
    ){
        this.BASE_URL = gatewayUrl + "/candidates";
        this.restTemplate = new RestTemplate();
    }

    public CandidateDto getCandidateById(UUID id) {
        this.logger.info("[CandidateClient] Fetching job by id: {}", id);
        try {
            String url = this.BASE_URL + "/" + id;
            this.logger.info("[CandidateClient] Trying to fetch job, url: {}", url);
            return restTemplate.getForObject(url, CandidateDto.class);

        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Usuário não encontrado: " + id);

        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Erro ao chamar Candidate Service: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            throw new RuntimeException("Falha ao conectar ao Candidate Service", e);
        }
    }

}
