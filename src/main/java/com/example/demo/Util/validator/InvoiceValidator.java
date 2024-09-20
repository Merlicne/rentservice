package com.example.demo.util.validator;

import com.example.demo.model.DormModel;
import com.example.demo.model.InvoiceModel;
import com.example.demo.model.RentModel;

public class InvoiceValidator {
    // validate model
    // private double waterUnit;  // input
    
    // private double electUnit; // input
    // private String monthly;  // input
    // private String dueDate; // input
    // private String recordDate; // input
    
    // private RentModel rent; // input rent id
    // private DormModel dorm; // input dorm id

    public static void validateInvoiceId(InvoiceModel invoiceModel) {
        if (invoiceModel.getInvoice_id() == null) {
            throw new IllegalArgumentException("Invoice ID is required");
        }
    }

    public static void validateWaterUnit(InvoiceModel invoiceModel) {
        if (invoiceModel.getWaterUnit() <= 0) {
            throw new IllegalArgumentException("Water unit must be greater than 0");
        }
    }

    public static void validateElectUnit(InvoiceModel invoiceModel) {
        if (invoiceModel.getElectUnit() <= 0) {
            throw new IllegalArgumentException("Electricity unit must be greater than 0");
        }
    }

    public static void validateMonthly(InvoiceModel invoiceModel) {
        if (invoiceModel.getMonthly() == null) {
            throw new IllegalArgumentException("Monthly is required");
        }
    }

    public static void validateDueDate(InvoiceModel invoiceModel) {
        if (invoiceModel.getDueDate() == null) {
            throw new IllegalArgumentException("Due date is required");
        }
    }

    public static void validateRecordDate(InvoiceModel invoiceModel) {
        if (invoiceModel.getRecordDate() == null) {
            throw new IllegalArgumentException("Record date is required");
        }
    }

    public static void validateRentId(InvoiceModel invoiceModel) {
        if (invoiceModel.getRent().getRentId() == null) {
            throw new IllegalArgumentException("Rent is required");
        }
    }

    public static void validateInvoice(InvoiceModel invoiceModel) {
        validateWaterUnit(invoiceModel);
        validateElectUnit(invoiceModel);
        validateMonthly(invoiceModel);
        validateDueDate(invoiceModel);
        validateRecordDate(invoiceModel);
        validateRentId(invoiceModel);
    }
    
 }
