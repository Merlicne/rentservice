package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Receipt;
import java.util.UUID;

public interface ReceiptRepository extends JpaRepository<Receipt, UUID>{
    // find by id which deleted at is null
    // @Query()
}
