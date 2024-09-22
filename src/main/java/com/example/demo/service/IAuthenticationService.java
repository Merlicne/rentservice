package com.example.demo.service;

import org.springframework.retry.annotation.Retryable;

import com.example.demo.model.JwtToken;
import com.example.demo.model.LoginRequest;

public interface IAuthenticationService {

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public JwtToken login(LoginRequest loginRequest);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public void isTenantValid(String tenantToken);
}
