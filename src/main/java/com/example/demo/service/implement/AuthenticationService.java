package com.example.demo.service.implement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Tenant;
import com.example.demo.exception.NotFoundException;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.JwtToken;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.TenantModel;
import com.example.demo.repository.TenantRepository;
import com.example.demo.service.IAuthenticationService;
import com.example.demo.util.TenantTokenGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final TenantRepository tenantRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public JwtToken login(LoginRequest loginRequest) {
        
        Tenant tenant = tenantRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new NotFoundException("Tenant not found"));
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
      
        );  

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", tenant.getRole());

        String token = jwtService.generateToken(claims, tenant);

        return JwtToken.builder()
                .token(token)
                .expiresIn(jwtService.getExpirationTime())
                .build();

    }

    @Override
    public void isTenantValid(String tenantToken) {
        tenantRepository.findByUsername(tenantToken)
                        .orElseThrow(() -> new NotFoundException("Tenant not found"));
    }

}
