package com.example.demo.Util.validator;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.TenantModel;
import com.example.demo.model.request.RentRequest;
import com.example.demo.entity.*;

public class TenantValidator {
    public static void validateTenantId(RentRequest rentModel) {
        if (rentModel.getTenant().getTenantId() < 0) {
            throw new BadRequestException("Tenant ID must be greater than 0");
        }
    }

    public static void validateTenantfName(RentRequest rentModel) {
        if (rentModel.getTenant().getFirstName() == null || rentModel.getTenant().getFirstName().isEmpty()) {
            throw new BadRequestException("Tenant first name cannot be empty");
        }
    }

    public static void validateTenantlName(RentRequest rentModel) {
        if (rentModel.getTenant().getLastName() == null || rentModel.getTenant().getLastName().isEmpty()) {
            throw new BadRequestException("Tenant last name cannot be empty");
        }
    }

    public static void validateTenantPhoneNum(RentRequest rentModel) {
        if (rentModel.getTenant().getPhoneNum() == null || rentModel.getTenant().getPhoneNum().isEmpty()) {
            throw new BadRequestException("Tenant phone number cannot be empty");
        }
    }

    public static void validateTenant(RentRequest rentModel) {
        validateTenantfName(rentModel);
        validateTenantlName(rentModel);
        validateTenantPhoneNum(rentModel);
    }
}
