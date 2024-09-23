package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.JwtToken;
import com.example.demo.model.ReceiptModel;
import com.example.demo.model.response.ResponseBody;
import com.example.demo.service.IReceiptService;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReceiptController {
    private final IReceiptService receiptService;

    @PostMapping("/invoice/{invoice_id}/receipt")
    public ResponseBody<ReceiptModel>  createReceipt(@RequestHeader("Authorization") String token, 
                                                    @RequestParam("image") MultipartFile file, 
                                                    @PathVariable("invoice_id") String invoice_id) {
                                                        
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        ReceiptModel response = receiptService.createReceipt(invoice_id, file, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(), "File uploaded successfully", response);
    }

    @GetMapping("/receipt/{id}")
    public ResponseBody<ReceiptModel> getReceiptById(@RequestHeader("Authorization") String token, 
                                                    @RequestParam("id") String id) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        ReceiptModel response = receiptService.getReceiptById(id, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(), "Receipt retrieved successfully", response);
    }

    @PutMapping("/receipt/{id}")
    public ResponseBody<ReceiptModel> updateReceipt(@RequestHeader("Authorization") String token, 
                                                    @RequestParam("image") MultipartFile file, 
                                                    @RequestParam("id") String id) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        ReceiptModel response = receiptService.updateReceipt(id, file, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(), "Receipt updated successfully", response);
    }

    @DeleteMapping("/receipt/delete/{id}")
    public ResponseBody<String> deleteReceipt(@RequestHeader("Authorization") String token, 
                                                @RequestParam("id") String id) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        receiptService.deleteReceipt(id, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(), "Receipt deleted successfully", "Receipt deleted successfully");
    }
    


    
}
