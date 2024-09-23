package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.JwtToken;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.response.ResponseBody;
import com.example.demo.service.IAuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tenant/auth")
public class TenantController {
    private final IAuthenticationService authenticationService;

    @GetMapping("/find/{token}")
    public ResponseBody<String> isTenantValid(@PathVariable String tenantToken) {
        authenticationService.isTenantValid(tenantToken);
        return new ResponseBody<>(200, "Tenant is valid", "Tenant is valid");
    }

    @PostMapping("/login")
    public ResponseBody<JwtToken> login(LoginRequest loginRequest) {
        JwtToken token = authenticationService.login(loginRequest);
        return new ResponseBody<>(200, "Login successful", token);
    }

}
