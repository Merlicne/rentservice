package com.example.demo.util.converter;

import java.time.LocalDate;
import java.util.UUID;


import com.example.demo.entity.Invoice;
import com.example.demo.entity.Rent;
import com.example.demo.model.DormModel;
import com.example.demo.model.InvoiceDetail;
import com.example.demo.model.InvoiceModel;
import com.example.demo.model.RentModel;
import com.example.demo.model.RoomModel;
import com.example.demo.model.TenantModel;
import com.example.demo.util.DateUtil;

public class InvoiceConverter {

        private InvoiceConverter() {
                throw new IllegalStateException("Utility class");
        }

        public static InvoiceModel toModel(Invoice invoiceEntity,
                        InvoiceDetail invoiceDetail,
                        DormModel dormModel,
                        RentModel rentModel,
                        TenantModel tenantModel,
                        RoomModel roomModel) {
                String monthly = DateUtil.toString(invoiceEntity.getMonthly());
                String dueDate = DateUtil.toString(invoiceEntity.getDueDate());
                String recordDate = DateUtil.toString(invoiceEntity.getRecordDate());

                return InvoiceModel.builder()
                                .dorm(DormModel.builder()
                                                .dormID(dormModel.getDormID())
                                                .name(dormModel.getName())
                                                .telephone(dormModel.getTelephone())
                                                .address(dormModel.getAddress())
                                                .build())
                                .invoiceId(invoiceEntity.getId().toString())
                                .status(invoiceEntity.getStatus())
                                .waterUnit(invoiceEntity.getWaterUnit())
                                .waterPrice(invoiceDetail.getWaterPrice())
                                .waterTotal(invoiceDetail.getWaterTotal())
                                .electUnit(invoiceEntity.getElectricUnit())
                                .electPrice(invoiceDetail.getElectPrice())
                                .electTotal(invoiceDetail.getElectTotal())
                                .roomUnit(invoiceDetail.getRoomUnit())
                                .roomPrice(invoiceDetail.getRoomPrice())
                                .roomTotal(invoiceDetail.getRoomTotal())
                                .total(invoiceDetail.getTotal())
                                .qrcode(invoiceDetail.getQrcode())
                                .monthly(monthly)
                                .dueDate(dueDate)
                                .recordDate(recordDate)
                                .rent(RentModel.builder()
                                                .rentId(rentModel.getRentId())
                                                .room(RoomModel.builder()
                                                                .roomID(roomModel.getRoomID())
                                                                .buildingID(roomModel.getBuildingID())
                                                                .roomNo(roomModel.getRoomNo())
                                                                .build())
                                                .tenant(TenantModel.builder()
                                                                .id(rentModel.getTenant().getId()
                                                                                .toString())
                                                                .firstName(rentModel.getTenant()
                                                                                .getFirstName())
                                                                .lastName(rentModel.getTenant()
                                                                                .getLastName())
                                                                .phoneNum(rentModel.getTenant()
                                                                                .getPhoneNum())
                                                                .build())

                                                .build())
                                .createdAt(invoiceEntity.getCreatedAt())
                                .updatedAt(invoiceEntity.getUpdatedAt())
                                .deletedAt(invoiceEntity.getDeletedAt())
                                .build();
        }

        public static Invoice toEntity(InvoiceModel invoiceModel, RentModel rentModel) {
                LocalDate monthly = DateUtil.toLocalDate(invoiceModel.getMonthly());
                LocalDate dueDate = DateUtil.toLocalDate(invoiceModel.getDueDate());
                LocalDate recordDate = DateUtil.toLocalDate(invoiceModel.getRecordDate());

                UUID invoiceId = null;
                if (invoiceModel.getInvoiceId() != null) {
                        invoiceId = UUID.fromString(invoiceModel.getInvoiceId());
                }
                Rent rent = RentConverter.toRentEntity(rentModel);

                return Invoice.builder()
                                .id(invoiceId)
                                .rent_id(rent.getRent_id())
                                .status(invoiceModel.getStatus())
                                .waterUnit(invoiceModel.getWaterUnit())
                                .electricUnit(invoiceModel.getElectUnit())
                                .recordDate(recordDate)
                                .qrcode(invoiceModel.getQrcode())
                                .monthly(monthly)
                                .dueDate(dueDate)
                                .recordDate(recordDate)
                                .createdAt(invoiceModel.getCreatedAt())
                                .updatedAt(invoiceModel.getUpdatedAt())
                                .deletedAt(invoiceModel.getDeletedAt())
                                .build();
        }
}
