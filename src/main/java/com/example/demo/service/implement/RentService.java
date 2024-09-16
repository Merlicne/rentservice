package com.example.demo.service.implement;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.Util.DateUtil;
import com.example.demo.Util.converter.RentConverter;
import com.example.demo.Util.validator.RentValidator;
import com.example.demo.Util.validator.TenantValidator;
import com.example.demo.entity.Rent;
import com.example.demo.entity.Tenant;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.request.RentRequest;
import com.example.demo.model.request.CancelRentRequest;
import com.example.demo.model.response.RentResponse;
import com.example.demo.repository.RentRepository;
import com.example.demo.repository.TenantRepository;
import com.example.demo.service.IRentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentService  implements IRentService {
    private final RentRepository rentRepository;
    private final TenantRepository tenantRepository;

    @Override
    public Iterable<RentResponse> getAllRents() {
        List<Rent> rents = rentRepository.findAllRents();
        return RentConverter.toRentModels(rents);
    }
    
    @Override
    public RentResponse getRentById(int id) {
        Rent rent = rentRepository.findRentById(id).orElseThrow(() -> new NotFoundException("Rent not found"));
        return RentConverter.toRentModel(rent);
    }

    @Override
    public RentResponse saveRent(RentRequest rentRequest) {
        
        RentValidator.validateRent(rentRequest);
        Rent rent = RentConverter.toRentEntity(rentRequest);

        TenantValidator.validateTenant(rentRequest);
        Tenant tenant = RentConverter.toTenantEntity(rentRequest);
        rent.setTenant(tenant);

        Rent r = rentRepository.save(rent);
        return RentConverter.toRentModel(r);
    }
    
    public RentResponse updateRent(int id, RentRequest rentRequest){
        // find by id 
        Rent r = rentRepository.findRentById(id).orElseThrow(() -> new NotFoundException("Rent not found"));
        
        rentRequest.setCreateAt(r.getCreateAt());

        RentValidator.validateRent(rentRequest);
        Rent rent = RentConverter.toRentEntity(rentRequest);

        TenantValidator.validateTenant(rentRequest);
        Tenant tenant = RentConverter.toTenantEntity(rentRequest);

        rent.setTenant(tenant);

        r = rentRepository.save(rent);
        return RentConverter.toRentModel(r);
        
    }
    
    public void deleteRent(int id){
        Rent rent = rentRepository.findRentById(id).orElseThrow(() -> new NotFoundException("Rent not found"));
        
        rent.setDeleteAt(LocalDateTime.now());
        rentRepository.save(rent);

    }

    public Iterable<RentResponse> getDeletedRents() {
        List<Rent> rents = rentRepository.findDeletedRents();
        return RentConverter.toRentModels(rents);
    }

    public RentResponse cancelRent(int rent_id, CancelRentRequest cancelRentRequest) {
        Rent r = rentRepository.findRentById(rent_id).orElseThrow(() -> new NotFoundException("Rent not found"));
        
        RentValidator.validateDateOut(cancelRentRequest);
        LocalDateTime createAt = r.getCreateAt();
        
        r.setDateOut(DateUtil.toLocalDate(cancelRentRequest.getDateOut()));
        r.setCreateAt(createAt);
        return RentConverter.toRentModel(rentRepository.save(r));

    }
    
    

}
