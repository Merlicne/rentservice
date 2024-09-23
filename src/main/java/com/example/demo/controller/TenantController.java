package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.JwtToken;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.RentModel;
import com.example.demo.model.TenantModel;
import com.example.demo.model.response.ResponseBody;
import com.example.demo.service.IAuthenticationService;
import com.example.demo.service.IRentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tenant")
public class TenantController {
    private final IAuthenticationService authenticationService;

    private final IRentService rentService;

    @GetMapping("/auth/find/{tenantToken}")
    public ResponseBody<String> isTenantValid(@PathVariable String tenantToken) {
        authenticationService.isTenantValid(tenantToken);
        return new ResponseBody<>(200, "Tenant is valid", "Tenant is valid");
    }

    @PostMapping("/auth/login")
    public ResponseBody<JwtToken> login(@RequestBody LoginRequest loginRequest) {
        JwtToken token = authenticationService.login(loginRequest);
        return new ResponseBody<>(200, "Login successful", token);
    }

    @GetMapping("/{id}/rent")
    public ResponseBody<RentModel> getTenantRents(@RequestHeader("Authorization") String token, @PathVariable String id) {
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        RentModel tenants = rentService.getRentByTenantId(id, jwtToken);
        return new ResponseBody<>(200, "Tenant retrieved successfully", tenants);
    }

    // @GetMapping("/{tenant_id}")
    // public ResponseBody<TenantModel> getTenantById(@RequestHeader("Authorization") String token, @PathVariable String tenant_id) {
    //     JwtToken jwtToken = JwtToken.builder().token(token).build();
    //     TenantModel tenant = rentService.getTenantById(tenant_id, jwtToken);
    //     return new ResponseBody<>(200, "Tenant retrieved successfully", tenant);
    // }

}
