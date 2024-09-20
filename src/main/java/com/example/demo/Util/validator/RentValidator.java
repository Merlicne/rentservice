package com.example.demo.util.validator;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.RentModel;
import com.example.demo.model.request.CancelRentRequest;

public class RentValidator {
    public static void validateRentId(RentModel rentModel) {
        if (rentModel.getRentId() == null) {
            throw new BadRequestException("Invalid rentId");
        }
    }

    public static void validatePeriod(RentModel rentModel) {
        if (rentModel.getPeriod() != 12 ) {
            throw new BadRequestException("Invalid period");
        }
    }

    public static void validateStartDate(RentModel rentModel) {
        System.out.println(rentModel.getStartDate());
        if (rentModel.getStartDate() == null ) {

            throw new BadRequestException("Invalid start date");
        }
    }

    // validate createdAt
    public static void validateCreatedAt(RentModel rentModel) {
        if (rentModel.getCreatedAt() == null) {
            throw new BadRequestException("Invalid createdAt");
        }
    }

    //validate updatedAt
    public static void validateUpdatedAt(RentModel rentModel) {
        if (rentModel.getUpdatedAt() == null) {
            throw new BadRequestException("Invalid updatedAt");
        }
    }

    // validate dateOut
    // public static void validateDateOut(RentRequest rentModel) {
    //     if (rentModel.getDateOut() == null) {
    //         throw new BadRequestException("Invalid dateOut");
    //     }
    // }

    public static void validateDateOut(CancelRentRequest cancelRentRequest) {
        if (cancelRentRequest.getDateOut() == null ) {
            throw new BadRequestException("Invalid dateOut");
        }
    }


    // public static void validateContract(RentModel rentModel) {
    //     if (rentModel.getContract() == null || rentModel.getContract().length == 0) {
    //         throw new BadRequestException("Invalid contract");
    //     }
        
    // }

    public static void validateRent(RentModel rentModel) {
        validatePeriod(rentModel);
        validateStartDate(rentModel);
    }

}
