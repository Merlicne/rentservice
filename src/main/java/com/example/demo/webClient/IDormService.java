package com.example.demo.webClient;

import org.springframework.retry.annotation.Retryable;

import com.example.demo.model.BuildingModel;
import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;

public interface IDormService {
    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public BuildingModel getBuilding(int id, JwtToken token);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public DormModel getDormInfo(JwtToken token);

}
