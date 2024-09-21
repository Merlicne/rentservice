package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDetail {
    private double waterUnit;  // input
    private double waterPrice;
    private double waterTotal;
    private double electUnit; // input
    private double electPrice;
    private double electTotal;
    private double roomUnit;
    private double roomPrice;
    private double roomTotal;
    private double total;
    private String qrcode;



    public InvoiceDetail(double waterUnit, double waterPrice, double electUnit, double electPrice, double roomPrice, String promptPay) {
        this.waterUnit = waterUnit;
        this.waterPrice = waterPrice;
        this.waterTotal = waterUnit * waterPrice;
        this.electUnit = electUnit;
        this.electPrice = electPrice;
        this.electTotal = electUnit * electPrice;
        this.roomUnit = 1;
        this.roomPrice = roomPrice;
        this.roomTotal = roomPrice;
        this.total = this.waterTotal + this.electTotal + this.roomTotal;
        this.qrcode = "https://promptpay.io/"+ promptPay + "/"+ this.total + ".png" ;
    }
}