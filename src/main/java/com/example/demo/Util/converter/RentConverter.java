package com.example.demo.util.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.example.demo.entity.Rent;
import com.example.demo.entity.Tenant;
import com.example.demo.model.RentModel;
import com.example.demo.model.RoomModel;
import com.example.demo.model.TenantModel;
import com.example.demo.util.DateUtil;


public class RentConverter {
    // save update
    public static Rent toRentEntity(RentModel rentModel) {
        LocalDate startDate = DateUtil.toLocalDate(rentModel.getStartDate());

        LocalDate dateOut = null;
        if (rentModel.getDateOut() != null) {
            dateOut = DateUtil.toLocalDate(rentModel.getDateOut());
        } 

        UUID rentID = null;
        if (rentModel.getRentId() != null) {
            rentID = UUID.fromString(rentModel.getRentId());
        }


        return Rent.builder()
            .rent_id(rentID)
            .room_id(rentModel.getRoom().getRoomID())
            .period(rentModel.getPeriod())
            .start_date(startDate)
            .dateOut(dateOut)
            .createdAt(rentModel.getCreatedAt())
            .updatedAt(rentModel.getUpdatedAt())
            .deletedAt(rentModel.getDeletedAt())
            .build();
    }

    public static Tenant toTenantEntity(RentModel rentModel) {
        UUID tenantId = null;
        if (rentModel.getTenant().getId() != null) {
            tenantId = UUID.fromString(rentModel.getTenant().getId());
        }

        return Tenant.builder()
            .Id(tenantId)
            .firstName(rentModel.getTenant().getFirstName())
            .lastName(rentModel.getTenant().getLastName())
            .phoneNum(rentModel.getTenant().getPhoneNum())
            .build();
    }

    public static RentModel toRentModel(Rent rent, Tenant tenant, RoomModel room) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate starDate = rent.getStart_date();
        String startDate_format = starDate.format(formatter);
        
        
        LocalDate dateOut = rent.getDateOut();
        String dateOut_format = null;
        if (dateOut != null) {
            dateOut_format = dateOut.format(formatter);
        }

        return RentModel.builder()
            // uuid
            .rentId(rent.getRent_id().toString())
            .period(rent.getPeriod())
            .startDate(startDate_format)
            .dateOut(dateOut_format)
            .room(RoomModel.builder()
                .roomID(room.getRoomID())
                .roomNo(room.getRoomNo())
                .build())
            .tenant(TenantModel.builder()
                .id(tenant.getId().toString())
                .firstName(tenant.getFirstName())
                .lastName(tenant.getLastName())
                .phoneNum(tenant.getPhoneNum())
                .build())
            .createdAt(rent.getCreatedAt())
            .updatedAt(rent.getUpdatedAt())
            .deletedAt(rent.getDeletedAt())
            .build();
       
    }


}
