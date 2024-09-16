package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Rent;
import com.example.demo.model.request.RentRequest;
import com.example.demo.model.request.CancelRentRequest;
import com.example.demo.model.response.RentResponse;
import com.example.demo.service.implement.RentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RentController {
    private final RentService rentService;

    // @PostMapping("/rent")
    // public Rent saveRent(@RequestBody RentRequestModel rent) {
    //     return rentService.saveRent(rent);
    // }

    @PostMapping("/rent")
    public @ResponseBody ResponseEntity<RentResponse> createRent(@RequestBody RentRequest rentRequest) {
        System.out.println(rentRequest);
        RentResponse rent = rentService.saveRent(rentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(rent);
    }

    @GetMapping("/rent/{rent_id}")
    public @ResponseBody ResponseEntity<RentResponse> getRentById(@PathVariable int rent_id) {
        RentResponse rent = rentService.getRentById(rent_id);
        return ResponseEntity.ok().body(rent);
    }

    @GetMapping("/rent")
    public @ResponseBody ResponseEntity<Iterable<RentResponse>> getAllRents() {
        Iterable<RentResponse> rents = rentService.getAllRents();
        return ResponseEntity.ok().body(rents);
    }

    @PutMapping("/rent/{rent_id}")
    public @ResponseBody ResponseEntity<RentResponse> updateRent(@PathVariable int rent_id, @RequestBody RentRequest rentRequest) {
        RentResponse rent = rentService.updateRent(rent_id, rentRequest);
        return ResponseEntity.ok().body(rent);
    }

    @DeleteMapping("/rent/{rent_id}")
    public @ResponseBody ResponseEntity<String> deleteRent(@PathVariable int rent_id) {
        rentService.deleteRent(rent_id);
        return ResponseEntity.ok().body("Rent deleted successfully");
    }

    @GetMapping("/rent/deleted")
    public @ResponseBody ResponseEntity<Iterable<RentResponse>> getDeletedRents() {
        Iterable<RentResponse> rents = rentService.getDeletedRents();
        return ResponseEntity.ok().body(rents);
    }

    @PutMapping("/rent/early-cancel/{rent_id}")
    public @ResponseBody ResponseEntity<RentResponse> earlyCancelRent(@PathVariable int rent_id, @RequestBody CancelRentRequest cancelRentRequest) {
        RentResponse rent = rentService.cancelRent(rent_id, cancelRentRequest);
        return ResponseEntity.ok().body(rent);
    }




}
