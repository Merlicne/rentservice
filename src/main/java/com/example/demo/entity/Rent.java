package com.example.demo.entity;

// import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Type;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private int rent_id;
    
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
    private Tenant tenant; // fk

    


    // @Lob
    // @Type(type = "org.hibernate.type.ImageType")
    // @Column(name = "contract")
    // private byte[] contract;

    @CreationTimestamp
    @Column(name = "createAt")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    // default NULL
    @Column(name = "deleteAt")
    private LocalDateTime deleteAt;

}
