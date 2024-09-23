package com.example.demo.service.implement;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Tenant;
import com.example.demo.exception.NotFoundException;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.JwtToken;
import com.example.demo.model.RentModel;
import com.example.demo.model.Role;
import com.example.demo.model.TenantModel;
import com.example.demo.repository.TenantRepository;
import com.example.demo.service.ITenantService;
import com.example.demo.util.converter.TenantConverter;
import com.example.demo.util.validator.RoleValidation;

import java.util.List;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TenantService implements ITenantService {
    private final TenantRepository tenantRepository;
    private final JwtService jwtService;

    public List<TenantModel> getAllTenants(JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        List<TenantModel> tenants = new ArrayList<>();
        for (Tenant t : tenantRepository.findAllTenants()) {
            tenants.add(TenantConverter.toTenantModel(t));
        }
        return tenants;
        
    }

    public TenantModel getTenantById(String id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        Tenant tenant = tenantRepository.findTenantById(id).orElseThrow(() -> new NotFoundException("Tenant not found"));
        return TenantConverter.toTenantModel(tenant);
    }

    
    // public TenantModel saveTenant(TenantModel tenantModel, JwtToken token) {
    //     Role role = jwtService.extractRole(token.getToken());
    //     RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

    //     Tenant tenant = TenantConverter.toTenantEntity(tenantModel);
    //     tenantRepository.save(tenant);
    //     return TenantConverter.toTenantModel(tenant);
    // }

    // public TenantModel updateTenant(String id, TenantModel tenant, JwtToken token) {
    //     Role role = jwtService.extractRole(token.getToken());
    //     RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

    //     Tenant tenantEntity = tenantRepository.findTenantById(id).orElseThrow(() -> new NotFoundException("Tenant not found"));
    //     TenantConverter.toTenantEntity(tenant)
    //     tenantRepository.save();
    // }

    
    // public void deleteTenant(String id, JwtToken token) {
    //     Role role = jwtService.extractRole(token.getToken());
    //     RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);
    // }

}
