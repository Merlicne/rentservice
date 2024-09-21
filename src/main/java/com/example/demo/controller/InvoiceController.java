package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.InvoiceModel;
import com.example.demo.model.JwtToken;
import com.example.demo.service.IInvoiceService;
import com.example.demo.model.response.ResponseBody;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class InvoiceController {
    private final IInvoiceService invoiceService;

    @GetMapping("/invoice")
    public ResponseBody<Iterable<InvoiceModel>> getAllInvoices(@RequestHeader("Authorization") String token) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        Iterable<InvoiceModel> invoices = invoiceService.getAllInvoices(jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Invoices retrieved successfully",invoices);
    }

    @GetMapping("/invoice/{invoice_id}")
    public ResponseBody<InvoiceModel> getInvoiceById(@RequestHeader("Authorization") String token, @PathVariable String invoice_id) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        
        InvoiceModel invoice = invoiceService.getInvoiceById(invoice_id, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Invoice retrieved successfully",invoice);
    }

    @GetMapping("/rent/{rent_id}/invoice")
    public ResponseBody<Iterable<InvoiceModel>> getInvoiceByRentId(@RequestHeader("Authorization") String token, @PathVariable String rent_id) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        Iterable<InvoiceModel> invoices = invoiceService.getInvoiceByRentId(rent_id, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Invoices retrieved successfully",invoices);
    }

    @PostMapping("/invoice")
    public ResponseBody<InvoiceModel> createInvoice(@RequestHeader("Authorization") String token, @RequestBody InvoiceModel invoiceModel) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        InvoiceModel invoice = invoiceService.createInvoice(invoiceModel, jwtToken);
        return new ResponseBody<>(HttpStatus.CREATED.value(), "Invoice created successfully",invoice);
    }

    @PutMapping("/invoice/{invoice_id}")
    public ResponseBody<InvoiceModel> updateInvoiceById(@RequestHeader("Authorization") String token, @PathVariable String invoice_id, @RequestBody InvoiceModel invoiceModel) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        InvoiceModel invoice = invoiceService.updateInvoiceById(invoice_id, invoiceModel, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Invoice updated successfully",invoice);
    }

    @DeleteMapping("/invoice/{invoice_id}")
    public ResponseBody<Void> deleteInvoiceById(@RequestHeader("Authorization") String token, @PathVariable String invoice_id) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        invoiceService.deleteInvoiceById(invoice_id, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Invoice deleted successfully",null);
    }
}
