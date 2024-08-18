package com.example.demo.entity;

// import java.sql.Date;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Rents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rents {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "room_id")
    private Long room_id;

    @Column(name = "tenant_id")
    private Long tenant_id;

    @Column(name = "start_date")
    private Instant start_date;

    @CreationTimestamp
    @Column(name = "createAt")
    private Instant createAt;

    @UpdateTimestamp
    @Column(name = "updateAt")
    private Instant updateAt;

    @Column(name = "isDeleted")
    private boolean isDeleted;

}
