package com.example.demo.entity;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Rent")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rent_id")
    private UUID rent_id;
    
    //room service
    @Column(name = "room_id")
    private int room_id;

    @Column(name = "period")
    private int period;
    
    @Column(name = "start_date")
    private LocalDate start_date;
    
    @Column(name = "dateOut")
    private LocalDate dateOut;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tenant_id")
    @JsonBackReference
    private Tenant tenant;
    
    @Lob
    // @Type(type="org.hibernate.type.PrimitiveByteArrayBlobType", value = java.lang.Byte[])
    // @Type(type  = "org.hibernate.type.BinaryType")
    // @JdbcTypeCode(Types.VARBINARY)
    @Column(name = "image_contract")
    private byte[] image_contract;


    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    // default NULL
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

}
