package com.example.demo.util.converter;

import java.util.UUID;

import com.example.demo.entity.Tenant;
import com.example.demo.model.RentModel;
import com.example.demo.model.TenantModel;

public class TenantConverter {
    public static Tenant toTenantEntity(RentModel rentModel) {

        return Tenant.builder()
            .Id(rentModel.getTenant().getId())
            .firstName(rentModel.getTenant().getFirstName())
            .lastName(rentModel.getTenant().getLastName())
            .phoneNum(rentModel.getTenant().getPhoneNum())
            .password(rentModel.getTenant().getPassword())
            .build();
    }

    // public static Tenant toTenantEntity(TenantModel tenantModel) {
    //     UUID tenantId = null;
    //     if (tenantModel.getId() != null) {
    //         tenantId = UUID.fromString(tenantModel.getId());
    //     }

    //     return Tenant.builder()
    //         .Id(tenantId)
    //         .firstName(tenantModel.getFirstName())
    //         .lastName(tenantModel.getLastName())
    //         .phoneNum(tenantModel.getPhoneNum())
    //         .password(tenantModel.getPassword())
    //         .token(tenantModel.getToken())
    //         .build();
    // }

    public static TenantModel toTenantModel(Tenant tenant) {
        return TenantModel.builder()
            .id(tenant.getId().toString())
            .firstName(tenant.getFirstName())
            .lastName(tenant.getLastName())
            .phoneNum(tenant.getPhoneNum())
            .password(tenant.getPassword())
            .build();
    }

    
}
