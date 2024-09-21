package com.example.demo.service;

import com.example.demo.model.InvoiceModel;
import com.example.demo.model.JwtToken;

public interface IInvoiceService {
    public InvoiceModel createInvoice(InvoiceModel invoiceModel, JwtToken token);
    public InvoiceModel updateInvoiceById(String invoice_id, InvoiceModel invoiceModel, JwtToken token);
    public InvoiceModel getInvoiceById(String invoice_id, JwtToken token);
    public Iterable<InvoiceModel> getInvoiceByRentId(String rent_id, JwtToken token);
    public Iterable<InvoiceModel> getAllInvoices(JwtToken token);
    public void deleteInvoiceById(String invoice_id, JwtToken token);
}
