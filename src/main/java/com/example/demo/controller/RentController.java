package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.ContractModel;
import com.example.demo.model.JwtToken;
import com.example.demo.model.RentModel;
import com.example.demo.model.response.ResponseBody;
import com.example.demo.service.IRentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RentController {
    private final IRentService rentService;

    @GetMapping("/rent")
    public  ResponseBody<Iterable<RentModel>> getAllRents(@RequestHeader("Authorization") String token) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        Iterable<RentModel> rents = rentService.getAllRents(jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Rents retrieved successfully",rents);
    }
    
    @GetMapping("/rent/{rent_id}")
    public ResponseBody<RentModel> getRentById(@RequestHeader("Authorization") String token, @PathVariable String rent_id) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        
        RentModel rent = rentService.getRentById(rent_id, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Rent retrieved successfully",rent);
    }
    
    // TODO เอา image ออก
    @PostMapping("/rent")
    public ResponseBody<RentModel> createRent(@RequestHeader("Authorization") String token, 
                                            @RequestBody RentModel rentRequest,
                                            @RequestParam("image") MultipartFile file) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        RentModel rent = rentService.saveRent(rentRequest, jwtToken);
        return new ResponseBody<>(HttpStatus.CREATED.value(),"Rent created successfully",rent);
    }

    // TODO เอา image ออก
    @PutMapping("/rent/{rent_id}")
    public ResponseBody<RentModel> updateRent(@RequestHeader("Authorization") String token, 
                                                @PathVariable String rent_id, 
                                                @RequestBody RentModel rentRequest ){
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        RentModel rent = rentService.updateRent(rent_id, rentRequest,  jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Rent updated successfully",rent);
    }

    @DeleteMapping("/rent/{rent_id}")
    public ResponseBody<String> deleteRent(@RequestHeader("Authorization") String token, @PathVariable String rent_id) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        rentService.deleteRent(rent_id, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Rent deleted successfully","Rent deleted successfully");
    }

    // TODO CRUD only contract image
    @PostMapping("/rent/{rent_id}/contract")
    public ResponseBody<ContractModel> createContract(@RequestHeader("Authorization") String token, 
                                                @PathVariable String rent_id,
                                                @RequestParam("image") MultipartFile file) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        ContractModel contractModel = rentService.saveContract(rent_id, file, jwtToken);
        return new ResponseBody<>(HttpStatus.CREATED.value(),"Contract created successfully",contractModel);
    }

    @GetMapping("/rent/{rent_id}/contract")
    public ResponseBody<ContractModel> getContract(@RequestHeader("Authorization") String token, @PathVariable String rent_id) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        ContractModel contractModel = rentService.getContract(rent_id, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Contract retrieved successfully",contractModel);
    }

    @PutMapping("/rent/{rent_id}/contract")
    public ResponseBody<ContractModel> updateContract(@RequestHeader("Authorization") String token, 
                                                @PathVariable String rent_id,
                                                @RequestParam("image") MultipartFile file) {
        token = token.substring(7); 
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        ContractModel contractModel = rentService.updateContract(rent_id, file, jwtToken);
        return new ResponseBody<>(HttpStatus.OK.value(),"Contract updated successfully",contractModel);
    }


}
