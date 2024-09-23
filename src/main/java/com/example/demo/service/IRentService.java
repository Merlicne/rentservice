package com.example.demo.service;


import java.io.IOException;

import org.springframework.retry.annotation.Retryable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.ContractModel;
import com.example.demo.model.JwtToken;
import com.example.demo.model.RentModel;

public interface IRentService {
    @Retryable(retryFor =   { Exception.class }, maxAttempts = 5)
    public Iterable<RentModel> getAllRents(JwtToken token);

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public RentModel getRentById(String id,  JwtToken token);

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public RentModel saveRent(RentModel rent,  JwtToken token);

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public RentModel updateRent(String id, RentModel rent,  JwtToken token);

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public void deleteRent(String id, JwtToken token);

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public ContractModel saveContract(String rent_id, MultipartFile file, JwtToken token) throws IOException;

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public ContractModel getContract(String rent_id, JwtToken token);

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public ContractModel updateContract(String rent_id, MultipartFile file, JwtToken token) throws IOException;

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5)
    public RentModel getRentByTenantId(String tenant_id, JwtToken token);
}
