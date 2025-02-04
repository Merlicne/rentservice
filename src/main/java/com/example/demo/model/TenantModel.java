package com.example.demo.model;

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
public class TenantModel {
    // maybe not used in this project 
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String password;
    private String token;
}
