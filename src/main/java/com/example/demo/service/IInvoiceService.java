package com.example.demo.service;

import org.springframework.retry.annotation.Retryable;

import com.example.demo.model.InvoiceModel;
import com.example.demo.model.JwtToken;

public interface IInvoiceService {
    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public InvoiceModel createInvoice(InvoiceModel invoiceModel, JwtToken token);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public InvoiceModel updateInvoiceById(String invoice_id, InvoiceModel invoiceModel, JwtToken token);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public InvoiceModel getInvoiceById(String invoice_id, JwtToken token);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public Iterable<InvoiceModel> getInvoiceByRentId(String rent_id, JwtToken token);
    
    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public Iterable<InvoiceModel> getAllInvoices(JwtToken token);

    @Retryable(value = { Exception.class }, maxAttempts = 5)
    public void deleteInvoiceById(String invoice_id, JwtToken token);

    
}
