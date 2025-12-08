package com.cc6.user.config; // O pacote deve ser onde você criou o arquivo

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

// A anotação @Configuration indica que esta classe contém configurações para o Spring.
@Configuration
// A anotação @OpenAPIDefinition contém os metadados globais da sua API.
@OpenAPIDefinition(
        info = @Info(
                title = "User Service API", // Título da sua documentação
                version = "1.0", // Versão da sua API
                description = "API REST para gerenciamento de informações básicas de usuários (cadastro, consulta e atualização)." // Descrição
        )
)
public class OpenAPIConfig {
    // Não precisa de código interno. As anotações fazem todo o trabalho.
}