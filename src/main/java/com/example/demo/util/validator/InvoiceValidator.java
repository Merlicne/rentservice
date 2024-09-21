package com.example.demo.util.validator;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.InvoiceModel;

public class InvoiceValidator {

    public static void validateInvoiceId(InvoiceModel invoiceModel) {
        if (invoiceModel.getInvoiceId() == null) {
            throw new BadRequestException("Invoice ID is required");
        }
    }

    public static void validateWaterUnit(InvoiceModel invoiceModel) {
        if (invoiceModel.getWaterUnit() <= 0) {
            throw new BadRequestException("Water unit must be greater than 0");
        }
    }

    public static void validateElectUnit(InvoiceModel invoiceModel) {
        if (invoiceModel.getElectUnit() <= 0) {
            throw new BadRequestException("Electricity unit must be greater than 0");
        }
    }

    public static void validateMonthly(InvoiceModel invoiceModel) {
        if (invoiceModel.getMonthly() == null) {
            throw new BadRequestException("Monthly is required");
        }
    }

    public static void validateDueDate(InvoiceModel invoiceModel) {
        if (invoiceModel.getDueDate() == null) {
            throw new BadRequestException("Due date is required");
        }
    }

    public static void validateRecordDate(InvoiceModel invoiceModel) {
        if (invoiceModel.getRecordDate() == null) {
            throw new BadRequestException("Record date is required");
        }
    }

    public static void validateRentId(InvoiceModel invoiceModel) {
        if (invoiceModel.getRent().getRentId() == null) {
            throw new BadRequestException("Rent is required");
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
