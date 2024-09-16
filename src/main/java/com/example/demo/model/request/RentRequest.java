package com.example.demo.model.request;

// import jakarta.persistence.Column;
import java.time.Instant;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class RentRequest {
    private int rentId;
    private int roomId;
    private int period;
    private String startDate;  // in the format "dd-MM-yyyy"
    private String dateOut; // in the format "dd-MM-yyyy"
    private TenantRequest tenant;

    @Data
    public static class TenantRequest {
        private int tenantId;
        private String firstName;
        private String lastName;
        private String phoneNum;
    }
    
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
    // private byte[] contract; // pdf, png, jpg file
}
