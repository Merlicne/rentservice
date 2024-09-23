package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import com.example.demo.entity.Tenant;


@Repository
public interface TenantRepository extends JpaRepository<Tenant, String>{

    // get tenant which not deleted
    @Query("SELECT t FROM Tenant t WHERE t.deletedAt IS NULL")
    public List<Tenant> findAllTenants();

    // get tenant by id which not deleted
    @Query("SELECT t FROM Tenant t WHERE t.id = ?1 AND t.deletedAt IS NULL")
    public Optional<Tenant> findTenantById(String id);

    // get deleted tenant
    @Query("SELECT t FROM Tenant t WHERE t.deletedAt IS NOT NULL")
    public List<Tenant> findDeletedTenants();

    @Query(value = "SELECT * FROM Tenants t WHERE t.id = ?1", nativeQuery=true)
    public Optional<Tenant> findByUsername(String id);

}
