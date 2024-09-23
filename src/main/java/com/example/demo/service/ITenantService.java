package com.example.demo.service;


import java.util.List;

import org.springframework.retry.annotation.Retryable;

import com.example.demo.entity.Rent;
import com.example.demo.model.JwtToken;
import com.example.demo.model.RentModel;
import com.example.demo.model.TenantModel;

public interface ITenantService {
    @Retryable(retryFor  = { Exception.class }, maxAttempts = 5)
    public List<TenantModel> getAllTenants(JwtToken jwtToken);

    @Retryable(retryFor =  { Exception.class }, maxAttempts = 5)
    public TenantModel getTenantById(int id, JwtToken jwtToken);

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public RentModel saveTenant(RentModel rent, JwtToken jwtToken);

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public TenantModel updateTenant(int id, TenantModel tenant, JwtToken jwtToken);

    @Retryable(retryFor =  { Exception.class }, maxAttempts = 5)
    public void deleteTenant(int id, JwtToken jwtToken);
}
