package com.example.demo.model;

import java.time.LocalDateTime;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceModel {
    private String invoiceId;  
    private InvoiceStatus status;
    
    private double waterUnit;  // input
    private double waterPrice;
    private double waterTotal;
    
    private double electUnit; // input
    private double electPrice;
    private double electTotal;
    
    private double roomUnit;
    private double roomPrice;
    private double roomTotal;
    private double total;

    private String qrcode;
    private String monthly;  // input
    private String dueDate; // input
    private String recordDate; // input
    
    private RentModel rent; // input rent id
    private DormModel dorm; // input dorm id
    // private 
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

// - invoiceId: string
// - status: InvoiceStatus
// - waterUnit: double
// - waterPrice: double
// - waterTotal: double
// - electUnit: double
// - electPrice: double
// - electTotal: double
// - roomUnit: double
// - roomPrice: double
// - roomTotal: double
// - total: double
// - qrcode: string
// - monthly: string
// - dueDate: string
// - recordDate: string
// - rent: RentModel
// - dorm: DormModel


}
