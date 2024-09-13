package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer>{
    
    // @Query("SELECT u FROM User u WHERE u.name = ?1")
    // User findUserByName(String name);

}
