package com.cc6.auth.clients;

import com.cc6.auth.dtos.CandidateRegistrationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "candidate-service", url = "${candidate-service.url}/v1/candidates")
public interface CandidateClient {

    @PostMapping
    Map<String, Object> createCandidate(@RequestBody Map<String, Object> request);
}
