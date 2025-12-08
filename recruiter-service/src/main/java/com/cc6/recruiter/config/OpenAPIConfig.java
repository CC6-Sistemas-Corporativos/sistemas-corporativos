package com.cc6.recruiter.config; // **ATENÇÃO:** O pacote deve ser este!

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Recruiter Service API",
                version = "1.0",
                description = "API REST para gerenciamento de perfis de recrutadores e suas operações de contratação."
        )
)
public class OpenAPIConfig {
    // Não precisa de nenhum código aqui. As anotações fazem o trabalho de configuração.
}