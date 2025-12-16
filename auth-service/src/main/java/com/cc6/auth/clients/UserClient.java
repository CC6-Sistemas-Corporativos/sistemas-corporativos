package com.cc6.auth.clients;

import com.cc6.auth.dtos.UserInfo;
import com.cc6.auth.dtos.UserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service-api", url = "${user-service.url}/v1/users")
public interface UserClient {

    @GetMapping("/{id}")
    UserInfo getUserById(@PathVariable UUID id);

    @GetMapping("/email/{email}")
    UserDetailsDto getUserByEmail(@PathVariable String email);
}
