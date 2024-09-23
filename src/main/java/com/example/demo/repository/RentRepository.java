package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.entity.Rent;


public interface RentRepository extends JpaRepository<Rent, UUID>{
    // get rent which not deleted
    @Query("SELECT r FROM Rent r WHERE r.deletedAt IS NULL")
    public List<Rent> findAllRents();

    // get rent by id which not deleted
    @Query("SELECT r FROM Rent r WHERE r.id = ?1 AND r.deletedAt IS NULL")
    public Optional<Rent> findRentById(UUID id);

    // get deleted rent
    @Query("SELECT r FROM Rent r WHERE r.deletedAt IS NOT NULL")
    public List<Rent> findDeletedRents();

    @Query(value = "SELECT r FROM Rent r WHERE r.tenant_id = ?1", nativeQuery = true)
    public Optional<Rent> findByTenantId(String tenant_id);
}
