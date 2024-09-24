package com.example.demo.util.converter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Rent;
import com.example.demo.entity.Tenant;
import com.example.demo.model.BuildingModel;
import com.example.demo.model.ContractModel;
import com.example.demo.model.RentModel;
import com.example.demo.model.RoomModel;
import com.example.demo.model.TenantModel;
import com.example.demo.util.DateUtil;
import com.example.demo.util.ImageUtil;


public class RentConverter {
    // save update
    public static Rent toRentEntity(RentModel rentModel, MultipartFile file) throws IOException {
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
            .image_contract(file.getBytes())
            .createdAt(rentModel.getCreatedAt())
            .updatedAt(rentModel.getUpdatedAt())
            .deletedAt(rentModel.getDeletedAt())
            .build();
    }

    // override
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

        int roomId = 0;
        if (rentModel.getRoom() != null) {
            roomId = rentModel.getRoom().getRoomID();
        }
        return Rent.builder()
            .rent_id(rentID)
            .room_id(roomId)
            .period(rentModel.getPeriod())
            .start_date(startDate)
            .dateOut(dateOut)
            .createdAt(rentModel.getCreatedAt())
            .updatedAt(rentModel.getUpdatedAt())
            .deletedAt(rentModel.getDeletedAt())
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
                .roomPrice(room.getRoomPrice())
                .buildingID(room.getBuildingID())
                .build())
            .tenant(TenantModel.builder()
                .id(tenant.getId())
                .firstName(tenant.getFirstName())
                .lastName(tenant.getLastName())
                .phoneNum(tenant.getPhoneNum())
                .password(tenant.getPassword())
                .build())
            .imageContract(rent.getImage_contract())
            .createdAt(rent.getCreatedAt())
            .updatedAt(rent.getUpdatedAt())
            .deletedAt(rent.getDeletedAt())
            .build();
    }

    public static ContractModel toContractModel(Rent rent) {
        return ContractModel.builder()
            .rent_id(rent.getRent_id().toString())
            .image_contract(rent.getImage_contract())
            .build();
    }
    
    public static ContractModel toContractEntity(Rent rent, MultipartFile file) throws IOException {
        return ContractModel.builder()
            .rent_id(rent.getRent_id().toString())
            .image_contract(file.getBytes())
            .build();
    }


}
