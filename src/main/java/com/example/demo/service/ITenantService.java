package com.example.demo.service;


import org.springframework.retry.annotation.Retryable;

import com.example.demo.entity.Rent;
import com.example.demo.model.RentModel;
import com.example.demo.model.TenantModel;

public interface ITenantService {
    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public Iterable<Rent> getAllTenants();

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public TenantModel getTenantById(int id);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public RentModel saveTenant(RentModel rent);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public TenantModel updateTenant(int id, TenantModel tenant);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public void deleteTenant(int id);
}
