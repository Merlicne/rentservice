package com.example.demo.service.implement;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Receipt;
import com.example.demo.model.JwtToken;
import com.example.demo.model.ReceiptModel;
import com.example.demo.model.Role;
import com.example.demo.repository.InvoiceRepository;
import com.example.demo.repository.ReceiptRepository;
import com.example.demo.service.IReceiptService;
import com.example.demo.util.converter.ReceiptConverter;
import com.example.demo.util.validator.RoleValidation;
import com.example.demo.exception.*;
import com.example.demo.middleware.JwtService;

import java.util.UUID;
import java.io.IOException;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptService implements IReceiptService {
    private final ReceiptRepository receiptRepository;
    private final InvoiceRepository invoiceRepository;
    private final JwtService jwtService;
    
    @Override
    public ReceiptModel getReceiptById(String re_id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        Receipt receipt= receiptRepository.findById(UUID.fromString(re_id)).orElseThrow(() -> new NotFoundException("Receipt not found"));
        return ReceiptConverter.toModel(receipt);
    }


    @Transactional
    public ReceiptModel createReceipt(String invoiceID, MultipartFile file, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        // check if they exist
        invoiceRepository.findById(UUID.fromString(invoiceID)).orElseThrow(() -> new NotFoundException("Invoice not found"));

        ReceiptModel receiptModel = new ReceiptModel();
        receiptModel.setInvoiceId(invoiceID);

        try {
            Receipt receipt = ReceiptConverter.toEntity(receiptModel, file);
            receipt = receiptRepository.save(receipt);
            return ReceiptConverter.toModel(receipt); 
        } catch (IOException e) {
            throw new BadRequestException("Failed to save image");
        }
    }


    @Transactional
    public ReceiptModel updateReceipt(String invoiceID, MultipartFile file, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        Receipt r = receiptRepository.findById(UUID.fromString(invoiceID)).orElseThrow(() -> new NotFoundException("Receipt not found"));
        ReceiptModel receiptModel = ReceiptConverter.toModel(r);

        try {
            Receipt receipt = ReceiptConverter.toEntity(receiptModel, file);
            receipt = receiptRepository.save(receipt);
            return ReceiptConverter.toModel(receipt); 
        } catch (IOException e) {
            throw new BadRequestException("Failed to save image");
        }
    }

    @Transactional
    public void deleteReceipt(String re_id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        Receipt receipt = receiptRepository.findById(UUID.fromString(re_id)).orElseThrow(() -> new NotFoundException("Receipt not found"));
        receipt.setDeletedAt(LocalDateTime.now());
        receiptRepository.save(receipt);
    }



}


