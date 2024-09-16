package com.example.demo.Util.validator;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.request.CancelRentRequest;
import com.example.demo.model.request.RentRequest;

public class RentValidator {
    public static void validateRentId(int id) {
        if (id < 0) {
            throw new BadRequestException("Invalid ID");
        }
    }

    public static void validatePeriod(RentRequest rentModel) {
        if (rentModel.getPeriod() != 12 ) {
            throw new BadRequestException("Invalid period");
        }
    }

    public static void validateStartDate(RentRequest rentModel) {
        System.out.println(rentModel.getStartDate());
        if (rentModel.getStartDate() == null || rentModel.getStartDate().isEmpty()) {

            throw new BadRequestException("Invalid start date");
        }
    }

    // validate createAt
    public static void validateCreateAt(RentRequest rentModel) {
        if (rentModel.getCreateAt() == null) {
            throw new BadRequestException("Invalid createAt");
        }
    }

    //validate updateAt
    public static void validateUpdateAt(RentRequest rentModel) {
        if (rentModel.getUpdateAt() == null) {
            throw new BadRequestException("Invalid updateAt");
        }
    }

    // validate dateOut
    public static void validateDateOut(RentRequest rentModel) {
        if (rentModel.getDateOut() == null || rentModel.getDateOut().isEmpty()) {
            throw new BadRequestException("Invalid dateOut");
        }
    }

    public static void validateDateOut(CancelRentRequest cancelRentRequest) {
        if (cancelRentRequest.getDateOut() == null || cancelRentRequest.getDateOut().isEmpty()) {
            throw new BadRequestException("Invalid dateOut");
        }
    }


    // public static void validateContract(RentModel rentModel) {
    //     if (rentModel.getContract() == null || rentModel.getContract().length == 0) {
    //         throw new BadRequestException("Invalid contract");
    //     }
        
    // }

    public static void validateRent(RentRequest rentModel) {
        // validate
        validateRentId(rentModel.getRentId());
        validatePeriod(rentModel);
        validateStartDate(rentModel);
        // validateContract(rentModel);

    }

}
