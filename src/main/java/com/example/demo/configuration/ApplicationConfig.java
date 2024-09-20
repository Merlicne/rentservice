package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {


    @Bean
    public WebClient webClientDormService(WebClient.Builder builder) {
        return builder.baseUrl("lb://dorm-service").build();
    }

    @Bean
    public WebClient webClientRoomService(WebClient.Builder builder) {
        return builder.baseUrl("lb://room-service").build();
    }
    
}
