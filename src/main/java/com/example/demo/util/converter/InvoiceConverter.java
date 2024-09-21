package com.example.demo.util.converter;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

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
            .invoice_id(invoiceEntity.getId().toString())
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
            .qrcode(invoiceEntity.getQrcode())
            .monthly(monthly)
            .dueDate(dueDate)
            .recordDate(recordDate)
            .rent(RentModel.builder()
                    .rentId(invoiceEntity.getRent().getRent_id().toString())
                    .room(RoomModel.builder()
                            .roomID(roomModel.getRoomID())
                            .roomNo(roomModel.getRoomNo())
                            .build()
                    )
                    .tenant(TenantModel.builder()
                            .id(invoiceEntity.getRent().getTenant().getId().toString())
                            .firstName(invoiceEntity.getRent().getTenant().getFirstName())
                            .lastName(invoiceEntity.getRent().getTenant().getLastName())
                            .phoneNum(invoiceEntity.getRent().getTenant().getPhoneNum())
                            .build()
                    )

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

        UUID invoice_id = null;
        if (invoiceModel.getInvoice_id() != null) {
            invoice_id = UUID.fromString(invoiceModel.getInvoice_id());
        }
        Rent rent = RentConverter.toRentEntity(rentModel);

        return Invoice.builder()
            .id(invoice_id)
            .rent(rent)
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

    // public static Iterable<InvoiceModel> toModels(Iterable<Invoice> invoices) {
    //     List<InvoiceModel> invoiceModels = new ArrayList<>();
    //     for (Invoice invoice : invoices) {
    //         invoiceModels.add(toModel(invoice));
    //     }
    //     return invoiceModels;
    // }

    // public static Iterable<Invoice> toEntities(Iterable<InvoiceModel> invoiceModels, Rent rent) {
    //     List<Invoice> invoices = new ArrayList<>();
    //     for (InvoiceModel invoiceModel : invoiceModels) {
    //         invoices.add(toEntity(invoiceModel, rent));
    //     }
    //     return invoices;
    // }
}
