package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID>{
    
    // @Query("SELECT u FROM User u WHERE u.name = ?1")
    // User findUserByName(String name);

    // get tenant which not deleted
    @Query("SELECT t FROM Tenant t WHERE t.deletedAt IS NULL")
    public Iterable<Tenant> findAllTenants();

    // get tenant by id which not deleted
    @Query("SELECT t FROM Tenant t WHERE t.id = ?1 AND t.deletedAt IS NULL")
    public Optional<Tenant> findTenantById(UUID id);

    // get deleted tenant
    @Query("SELECT t FROM Tenant t WHERE t.deletedAt IS NOT NULL")
    public Iterable<Tenant> findDeletedTenants();

}
