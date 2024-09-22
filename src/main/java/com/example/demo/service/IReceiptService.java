package com.example.demo.service;

import org.springframework.retry.annotation.Retryable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.JwtToken;
import com.example.demo.model.ReceiptModel;

public interface IReceiptService {
    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public ReceiptModel createReceipt(String invoiceID, MultipartFile file, JwtToken token);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public ReceiptModel getReceiptById(String id, JwtToken token);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public ReceiptModel updateReceipt(String id, MultipartFile file, JwtToken token);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public void deleteReceipt(String id, JwtToken token);
    

}
