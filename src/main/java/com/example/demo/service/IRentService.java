package com.example.demo.service;


import com.example.demo.model.JwtToken;
import com.example.demo.model.RentModel;

public interface IRentService {
    public Iterable<RentModel> getAllRents(JwtToken token);
    public RentModel getRentById(String id, JwtToken token);
    public RentModel saveRent(RentModel rent, JwtToken token);
    public RentModel updateRent(String id, RentModel rent, JwtToken token);
    public void deleteRent(String id, JwtToken token);
}
