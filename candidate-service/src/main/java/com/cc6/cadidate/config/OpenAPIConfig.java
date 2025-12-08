package com.cc6.cadidate.config; // Ajuste o pacote para onde você criou o arquivo

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Candidate Service API",
                version = "1.0",
                description = "API REST para gerenciamento de dados de candidatos e seus currículos."
        )
)
public class OpenAPIConfig {
    // A anotação @Configuration diz ao Spring para processar as anotações do Swagger.
}