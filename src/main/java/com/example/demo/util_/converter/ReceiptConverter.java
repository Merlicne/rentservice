package com.example.demo.util_.converter;

import java.io.IOException; // Add this import statement

import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Receipt;
import com.example.demo.model.ReceiptModel;
import com.example.demo.util_.ImageUtil;

public class ReceiptConverter {

    // get, show output
    public static ReceiptModel toModel(Receipt receiptEntity) {

        return ReceiptModel.builder()
                // .receipt_id(receiptEntity.getReceipt_id().toString())
                    .invoice_id(receiptEntity.getInvoice_id().toString())
                    .type(receiptEntity.getType())
                    .image_receipt(ImageUtil.decompressImage(receiptEntity.getImage_receipt()))
                    .fileName(receiptEntity.getFileName())
                    .size(receiptEntity.getSize())
                    // .uri(receiptEntity.getUri())
                    .createdAt(receiptEntity.getCreatedAt())
                    .updatedAt(receiptEntity.getUpdatedAt())
                    .deletedAt(receiptEntity.getDeletedAt())
                    .build();

                
    }

    //save , update
    public static Receipt toEntity(ReceiptModel receiptModel, MultipartFile file) throws IOException {
        // UUID invoice_uuid = null;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();
         
        // String fileCode = FileUploadUtil.saveFile(fileName, file);

        return Receipt.builder()
                .invoice_id(UUID.fromString(receiptModel.getInvoice_id()))
                .type(file.getContentType())
                .image_receipt(ImageUtil.compressImage(file.getBytes()))
                .fileName(fileName)
                .size(size)
                // .uri("/downloadFile/" + fileCode)
                .createdAt(receiptModel.getCreatedAt())
                .updatedAt(receiptModel.getUpdatedAt())
                .deletedAt(receiptModel.getDeletedAt())
                .build();
    }


}
