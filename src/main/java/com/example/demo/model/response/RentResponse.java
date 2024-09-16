package com.example.demo.model.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentResponse {
    private int rentId;
    private int roomId;
    private int period;
    private String startDate;  // in the format "dd-MM-yyyy"
    // private String endDate; //format DateTime
    private String dateOut; // in the format "dd-MM-yyyy"
    private TenantResponse tenant;

    @Data
    @Builder
    public static class TenantResponse {
        private int tenantId;
        private String firstName;
        private String lastName;
        private String phoneNum;
    }

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
}
