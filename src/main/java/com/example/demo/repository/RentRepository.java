package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Rent;

public interface RentRepository extends JpaRepository<Rent, Integer>{

}
