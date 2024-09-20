package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.JwtToken;
import com.example.demo.model.ReceiptModel;

public interface IReceiptService {
    public ReceiptModel createReceipt(String invoiceID, MultipartFile file, JwtToken token);
    public ReceiptModel getReceiptById(String id, JwtToken token);
    public ReceiptModel updateReceipt(String id, MultipartFile file, JwtToken token);
    public void deleteReceipt(String id, JwtToken token);
    

}
