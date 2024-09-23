package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.enumurated.RoomStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomModel {
    private int roomID ;
    // private int buildingID;
    private int buildingID;
    private String roomNo;
    private int roomPrice;
    private RoomStatus roomStatus;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
}
