package com.example.demo.service;

import com.example.demo.entity.Rent;
import com.example.demo.model.request.RentRequest;
import com.example.demo.model.response.RentResponse;

public interface IRentService {
    public Iterable<RentResponse> getAllRents();
    public RentResponse getRentById(int id);
    public RentResponse saveRent(RentRequest rent);
    public RentResponse updateRent(int id, RentRequest rent);
    public void deleteRent(int id);
}
