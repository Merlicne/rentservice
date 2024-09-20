package com.example.demo.model;

import java.time.LocalDateTime;

import com.example.demo.entity.Invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReceiptModel {
    // private String receipt_id;

    private String invoice_id;
    
    private String type;
    private String fileName;
    private long size;
    // private String uri;

    private byte[] image_receipt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
