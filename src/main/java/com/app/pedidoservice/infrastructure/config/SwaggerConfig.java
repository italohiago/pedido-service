package com.app.pedidoservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()) // 🔹 Necessário para estruturar o OpenAPI corretamente
                .info(new Info()
                        .title("Pedido Service API")
                        .version("1.0")
                        .description("Documentação da API de pedidos."));
    }
}
