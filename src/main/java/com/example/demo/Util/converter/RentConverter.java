package com.example.demo.Util.converter;

// import java.text.DateFormat;
// import java.time.Instant;
import java.time.LocalDate;
// import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
// import java.util.*;
import java.util.List;

import com.example.demo.Util.DateUtil;
import com.example.demo.entity.Rent;
import com.example.demo.entity.Tenant;
import com.example.demo.model.TenantModel;
import com.example.demo.model.request.RentRequest;
import com.example.demo.model.response.RentResponse;

public class RentConverter {
    public static Rent toRentEntity(RentRequest rentRequest) {
        LocalDate startDate = DateUtil.toLocalDate(rentRequest.getStartDate());
        LocalDate dateOut = null;
        if (rentRequest.getDateOut() != null) {
            dateOut = DateUtil.toLocalDate(rentRequest.getDateOut());
        } 
        


        return Rent.builder()
            .rent_id(rentRequest.getRentId())
            .room_id(rentRequest.getRoomId())
            .period(rentRequest.getPeriod())
            .start_date(startDate)
            .dateOut(dateOut)
            .createAt(rentRequest.getCreateAt())
            .updateAt(rentRequest.getUpdateAt())
            .deleteAt(rentRequest.getDeleteAt())
            .build();
    }

    public static Tenant toTenantEntity(RentRequest rentModel) {
        return Tenant.builder()
            .Id(rentModel.getTenant().getTenantId())
            .firstName(rentModel.getTenant().getFirstName())
            .lastName(rentModel.getTenant().getLastName())
            .phoneNum(rentModel.getTenant().getPhoneNum())
            .build();
    }

    public static RentResponse toRentModel(Rent rent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate starDate = rent.getStart_date();
        String startDate_format = starDate.format(formatter);
        
        
        LocalDate dateOut = rent.getDateOut();
        String dateOut_format = null;
        if (dateOut != null) {
            dateOut_format = dateOut.format(formatter);
        }


        return RentResponse.builder()
            .rentId(rent.getRent_id())
            .roomId(rent.getRoom_id())
            .period(rent.getPeriod())
            .startDate(startDate_format)
            .dateOut(dateOut_format)
            .createAt(rent.getCreateAt())
            .updateAt(rent.getUpdateAt())
            .deleteAt(rent.getDeleteAt())
            .tenant(RentResponse.TenantResponse.builder()
                .tenantId(rent.getTenant().getId())
                .firstName(rent.getTenant().getFirstName())
                .lastName(rent.getTenant().getLastName())
                .phoneNum(rent.getTenant().getPhoneNum())
                .build())
            .build();

        
        
    }

    public static List<RentResponse> toRentModels(List<Rent> rents) {
        List<RentResponse> rentResponse = new ArrayList<RentResponse>();

        for (Rent rent : rents) {
            rentResponse.add(toRentModel(rent));
        }

        return rentResponse;
    }

    // public static TenantModel toTenantModel(RentRequestModel rentModel) {
    //     return TenantModel.builder()
    //         .id(rentModel.getTenant_id())
    //         // .tenant_id(rentModel.getTenant_id())
    //         .firstName(rentModel.getFirstName())
    //         .lastName(rentModel.getLastName())
    //         .phoneNum(rentModel.getPhoneNum())
    //         .build();
        
    // }
    // // rent model -> tenant entity
    // public static Tenant toTenantEntity(RentRequestModel rentModel) {
        
    //     return Tenant.builder()
    //         .Id(rentModel.getTenant_id())
    //         .firstName(rentModel.getFirstName())
    //         .lastName(rentModel.getLastName())
    //         .phoneNum(rentModel.getPhoneNum())
    //         .build();
    // }

    // public static RentRequestModel toRentModel(Rent rent) {
    //     LocalDate starDate = rent.getStart_date();
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //     String startDate_format = starDate.format(formatter);

    //     return RentRequestModel.builder()
    //         .rent_id(rent.getRent_id())
    //         .room_id(rent.getRoom_id())
    //         .period(rent.getPeriod())
    //         .start_date(startDate_format)
    //         // .contract(rent.getContract())
    //         .build();
    // }

    // public static RentRequestModel toRentModel(Rent rent, Tenant tenant) {
    //     LocalDate starDate = rent.getStart_date();
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //     String startDate_format = starDate.format(formatter);

    //     return RentRequestModel.builder()
    //         .rent_id(rent.getRent_id())
    //         .room_id(rent.getRoom_id())
    //         .period(rent.getPeriod())
    //         .start_date(startDate_format)
    //         .tenant_id(tenant.getId())
    //         // .contract(rent.getContract())
    //         .firstName(tenant.getFirstName())
    //         .lastName(tenant.getLastName())
    //         .phoneNum(tenant.getPhoneNum())
    //         .build();
    // }

    // public static Rent toRentEntity(RentRequestModel rentModel, int tenant_id) {

    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //     LocalDate startDate = LocalDate.parse(rentModel.getStart_date(), formatter);
       

    //     return Rent.builder()
    //         .rent_id(rentModel.getRent_id())
    //         .room_id(rentModel.getRoom_id())
    //         // .tenant_id(tenant_id)

    //         .period(rentModel.getPeriod())
    //         .start_date(startDate)
    //         // .contract(rentModel.getContract())
    //         .build();
    // }

    // // override method
    // public static Rent toRentEntity(RentRequestModel rentModel, Tenant tenant) {
        
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //     LocalDate startDate = LocalDate.parse(rentModel.getStart_date(), formatter);
        
    //     return Rent.builder()
    //         .rent_id(rentModel.getRent_id())
    //         .room_id(rentModel.getRoom_id())
    //         .tenant(tenant)
    //         .period(rentModel.getPeriod())
    //         .start_date(startDate)
    //         // .contract(rentModel.getContract())
    //         .build();
    // }

}
