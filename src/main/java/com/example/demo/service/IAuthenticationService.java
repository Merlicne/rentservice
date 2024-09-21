package com.example.demo.service;

import com.example.demo.model.JwtToken;
import com.example.demo.model.LoginRequest;

public interface IAuthenticationService {
    public JwtToken login(LoginRequest loginRequest);
    public void isTenantValid(String tenantToken);
}
