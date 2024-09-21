package com.example.demo.model;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentModel {
    private String rentId;
    private int period;
    private String startDate;  // in the format "dd-MM-yyyy"
    private String dateOut; // in the format "dd-MM-yyyy"
    
    private RoomModel room;
    private TenantModel tenant;
    private byte[] imageContract;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
