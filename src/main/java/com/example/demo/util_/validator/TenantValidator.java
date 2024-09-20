package com.example.demo.util_.validator;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.RentModel;


public class TenantValidator {
    // public static void validateTenantId(RentRequest rentModel) {
    //     if (UUID.fromString(rentModel.getTenant().getTenantId()) == null) {
    //         throw new BadRequestException("Tenant ID must be greater than 0");
    //     }
    // }

    public static void validateTenantFirstName(RentModel rentModel) {
        if (rentModel.getTenant().getFirstName() == null || rentModel.getTenant().getFirstName().isEmpty()) {
            throw new BadRequestException("Tenant first name cannot be empty");
        }
    }

    public static void validateTenantLastName(RentModel rentModel) {
        if (rentModel.getTenant().getLastName() == null || rentModel.getTenant().getLastName().isEmpty()) {
            throw new BadRequestException("Tenant last name cannot be empty");
        }
    }

    public static void validateTenantPhoneNum(RentModel rentModel) {
        if (rentModel.getTenant().getPhoneNum() == null || rentModel.getTenant().getPhoneNum().isEmpty()) {
            throw new BadRequestException("Tenant phone number cannot be empty");
        }
    }

    public static void validateTenant(RentModel rentModel) {
        validateTenantFirstName(rentModel);
        validateTenantLastName(rentModel);
        validateTenantPhoneNum(rentModel);
    }
}
