package com.example.demo.repository;

import java.util.UUID;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID>{
    //get invoice which deletedAt is null
    @Query("SELECT i FROM Invoice i WHERE i.rent_id = ?1 AND i.deletedAt IS NULL ORDER BY i.dueDate DESC")
    public List<Invoice> findByRentId(UUID rent_id);

    //get All invoices which deletedAt is null
    @Query(value = "SELECT * FROM Invoice WHERE deleted_at IS NULL", nativeQuery = true)
    public List<Invoice> findAllInvoices(); 


    


}
