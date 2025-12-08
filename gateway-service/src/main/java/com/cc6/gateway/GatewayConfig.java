package com.cc6.gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter logFilter() {
        return (exchange, chain) -> {
            System.out.println("➡️ Requisição: " + exchange.getRequest().getURI());
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    System.out.println("⬅️ Resposta enviada: " + exchange.getResponse().getStatusCode())
            ));
        };
    }
}
