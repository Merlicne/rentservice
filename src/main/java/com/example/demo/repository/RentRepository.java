package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import com.example.demo.entity.Rent;

public interface RentRepository extends JpaRepository<Rent, Integer>{
    // get rent which not deleted
    @Query("SELECT r FROM Rent r WHERE r.deleteAt IS NULL")
    public List<Rent> findAllRents();

    // get rent by id which not deleted
    @Query("SELECT r FROM Rent r WHERE r.id = ?1 AND r.deleteAt IS NULL")
    public Optional<Rent> findRentById(int id);

    // get deleted rent
    @Query("SELECT r FROM Rent r WHERE r.deleteAt IS NOT NULL")
    public List<Rent> findDeletedRents();
}
