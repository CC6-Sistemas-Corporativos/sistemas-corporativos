package com.cc6.job.clients;

import com.cc6.job.dtos.recruiter.RecruiterDto;
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
            @Value("${gateway.url}") String gatewayUrl
    ){
        this.BASE_URL = gatewayUrl + "/recruiters";
        this.restTemplate = new RestTemplate();
    }

    public RecruiterDto getRecruiterById(UUID id) {
        this.logger.info("[RecruiterClient] Fetching job by id: {}", id);
        try {
            String url = this.BASE_URL + "/" + id;
            this.logger.info("[RecruiterClient] Trying to fetch job, url: {}", url);
            return restTemplate.getForObject(url, RecruiterDto.class);

        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Usuário não encontrado: " + id);

        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Erro ao chamar Recruiter Client: " + e.getResponseBodyAsString());

        } catch (Exception e) {
            throw new RuntimeException("Falha ao conectar ao Recruiter Client", e);
        }
    }

}
