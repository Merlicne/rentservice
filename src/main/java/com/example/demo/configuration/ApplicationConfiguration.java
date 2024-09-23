package com.example.demo.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.TenantRepository;

import lombok.RequiredArgsConstructor;
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final TenantRepository tenantRepository;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> tenantRepository.findByUsername(username)
                                                        .orElseThrow(() -> new NotFoundException("User not found on Authentication"));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    @LoadBalanced  // Ensures the lb:// scheme is recognized
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean(name = "webClientDormService")
    public WebClient webClientDormClient(WebClient.Builder builder) {
        return builder.baseUrl("lb://dorm-service/api/v1").build();
    }

    @Bean(name = "webClientRoomService")
    public WebClient webClientRoomClient(WebClient.Builder builder) {
        return builder.baseUrl("lb://room-service/api/v1").build();
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


}
