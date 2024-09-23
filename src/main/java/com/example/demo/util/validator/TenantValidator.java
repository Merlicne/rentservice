package com.example.demo.util.validator;

import com.example.demo.exception.BadRequestException;
import com.example.demo.entity.Tenant;


public class TenantValidator {
    // public static void validateTenantId(RentRequest Tenant) {
    //     if (UUID.fromString(Tenant()Id()) == null) {
    //         throw new BadRequestException("Tenant ID must be greater than 0");
    //     }
    // }

    public static void validateTenantFirstName(Tenant Tenant) {
        if (Tenant.getFirstName() == null || Tenant.getFirstName().isEmpty()) {
            throw new BadRequestException("Tenant first name cannot be empty");
        }
    }

    public static void validateTenantLastName(Tenant Tenant) {
        if (Tenant.getLastName() == null || Tenant.getLastName().isEmpty()) {
            throw new BadRequestException("Tenant last name cannot be empty");
        }
    }

    public static void validateTenantPhoneNum(Tenant Tenant) {
        if (Tenant.getPhoneNum() == null || Tenant.getPhoneNum().isEmpty()) {
            throw new BadRequestException("Tenant phone number cannot be empty");
        }
    }

    // validate password
    public static void validatePassword(Tenant Tenant) {
        if (Tenant.getPassword() == null || Tenant.getPassword().isEmpty()) {
            throw new BadRequestException("Tenant password cannot be empty");
        }
    }


    public static void validateTenant(Tenant Tenant) {
        validateTenantFirstName(Tenant);
        validateTenantLastName(Tenant);
        validateTenantPhoneNum(Tenant);
        validatePassword(Tenant);
    }
}
