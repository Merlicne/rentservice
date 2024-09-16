package com.example.demo.service;


import com.example.demo.entity.Rent;
import com.example.demo.entity.Tenant;
// import com.example.demo.entity.Tenant;
import com.example.demo.model.TenantModel;
// import com.example.demo.service.implement.RentService;
import com.example.demo.model.request.RentRequest;

public interface ITenantService {
    public Iterable<Rent> getAllTenants();
    public TenantModel getTenantById(int id);
    public RentRequest saveTenant(RentRequest rent);
    public TenantModel updateTenant(int id, TenantModel tenant);
    public void deleteTenant(int id);
}
