package com.example.demo.service;


import com.example.demo.entity.Rent;
import com.example.demo.model.RentModel;
import com.example.demo.model.TenantModel;

public interface ITenantService {
    public Iterable<Rent> getAllTenants();
    public TenantModel getTenantById(int id);
    public RentModel saveTenant(RentModel rent);
    public TenantModel updateTenant(int id, TenantModel tenant);
    public void deleteTenant(int id);
}
