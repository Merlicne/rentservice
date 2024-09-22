package com.example.demo.service.implement;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.example.demo.entity.Rent;
import com.example.demo.entity.Tenant;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.ContractModel;
import com.example.demo.model.JwtToken;
import com.example.demo.model.RentModel;
import com.example.demo.model.Role;
import com.example.demo.model.RoomModel;
import com.example.demo.repository.RentRepository;
import com.example.demo.repository.TenantRepository;
import com.example.demo.service.IRentService;
import com.example.demo.util.TenantTokenGenerator;
import com.example.demo.util.converter.RentConverter;
import com.example.demo.util.converter.TenantConverter;
import com.example.demo.util.validator.RentValidator;
import com.example.demo.util.validator.RoleValidation;
import com.example.demo.util.validator.TenantValidator;
import com.example.demo.webClient.IRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentService implements IRentService {
    private final RentRepository rentRepository;
    private final TenantRepository tenantRepository;

    private final IRoomService roomService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Iterable<RentModel> getAllRents(JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        List<Rent> rents = rentRepository.findAllRents();

        List<RentModel> rentModels = new ArrayList<>();
        for (Rent rent : rents) {
            Tenant tenant = tenantRepository.findTenantById(rent.getTenant().getId())
                    .orElseThrow(() -> new NotFoundException("Tenant not found"));
            RoomModel room = roomService.getRoom(rent.getRoom_id(), token);
            rentModels.add(RentConverter.toRentModel(rent, tenant, room));
        }
        return rentModels;
    }

    public RentModel getRentById(String id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        UUID uuid = UUID.fromString(id);
        Rent rent = rentRepository.findRentById(uuid).orElseThrow(() -> new NotFoundException("Rent not found"));
        Tenant tenant = tenantRepository.findTenantById(rent.getTenant().getId())
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
        RoomModel room = roomService.getRoom(rent.getRoom_id(), token);
        return RentConverter.toRentModel(rent, tenant, room);
    }

    @Transactional
    public RentModel saveRent(RentModel rentRequest, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        // validate
        RentValidator.validateRent(rentRequest);
        
        Rent rent = RentConverter.toRentEntity(rentRequest);
        
        RoomModel room = roomService.getRoom(rentRequest.getRoom().getRoomID(), token);
        
        // save tenant to repository
        Tenant tenant = TenantConverter.toTenantEntity(rentRequest);
        tenant.setToken(TenantTokenGenerator.generateToken(tenant.getPhoneNum()));
        tenant.setPassword(passwordEncoder.encode(tenant.getPhoneNum()));
        tenant.setRole(Role.TENANT);
        TenantValidator.validateTenant(tenant);
        Tenant tenant_repo = tenantRepository.save(tenant);
        
        rent.setTenant(tenant_repo);
        rent = rentRepository.save(rent);
        RentModel rentModel =  RentConverter.toRentModel(rent, tenant_repo, room);
        
        return rentModel;
    }

    @Transactional
    public RentModel updateRent(String rentId, RentModel rentRequest,  JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        // rent
        UUID rentUuid = UUID.fromString(rentId);
        Rent rent = rentRepository.findRentById(rentUuid).orElseThrow(() -> new NotFoundException("Rent not found"));

        RentValidator.validateRent(rentRequest);
        Rent newRent = RentConverter.toRentEntity(rentRequest);
        
        newRent.setCreatedAt(rent.getCreatedAt());

        // tenant
        UUID tenantUuid = rent.getTenant().getId();
        Tenant tenant = tenantRepository.findTenantById(tenantUuid).orElseThrow(() -> new NotFoundException("Tenant not found"));

        Tenant newTenant = TenantConverter.toTenantEntity(rentRequest);
        newTenant.setToken(TenantTokenGenerator.generateToken(newTenant.getPhoneNum()));
        newTenant.setPassword(passwordEncoder.encode(newTenant.getPhoneNum()));
        newTenant.setRole(Role.TENANT);
        newTenant.setCreatedAt(tenant.getCreatedAt());
        newTenant.setId(tenantUuid);
        TenantValidator.validateTenant(newTenant);


        // save to db
        newTenant = tenantRepository.save(newTenant);
        newRent.setTenant(newTenant);
        newRent = rentRepository.save(newRent);
        RoomModel room = roomService.getRoom(rentRequest.getRoom().getRoomID(), token);
        return RentConverter.toRentModel(newRent, newTenant, room);
    }

    @Transactional
    public void deleteRent(String id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        UUID uuid = UUID.fromString(id);
        Rent rent = rentRepository.findRentById(uuid).orElseThrow(() -> new NotFoundException("Rent not found"));

        rent.setDeletedAt(LocalDateTime.now());
        rentRepository.save(rent);
    }

// contract image management
    public ContractModel saveContract(String rent_id, MultipartFile file, JwtToken token) throws IOException {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        UUID rentUuid = UUID.fromString(rent_id);
        Rent rent = rentRepository.findRentById(rentUuid).orElseThrow(() -> new NotFoundException("Rent not found"));
        ContractModel contractModel;
        try {
            // rent.setImage_contract(file.getBytes());
            contractModel= RentConverter.toContractEntity(rent, file);
        } catch (Exception e) {
            throw new BadRequestException("Failed to save image" + e.getMessage());
        }

        rent.setImage_contract(contractModel.getImage_contract());
        rent = rentRepository.save(rent);
        return RentConverter.toContractModel(rent);
    }

    public ContractModel getContract(String rent_id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        UUID rentUuid = UUID.fromString(rent_id);
        Rent rent = rentRepository.findRentById(rentUuid).orElseThrow(() -> new NotFoundException("Rent not found"));

        return RentConverter.toContractModel(rent);
    }

    public ContractModel updateContract(String rent_id, MultipartFile file, JwtToken token) throws IOException {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        UUID rentUuid = UUID.fromString(rent_id);
        Rent rent = rentRepository.findRentById(rentUuid).orElseThrow(() -> new NotFoundException("Rent not found"));
        ContractModel contractModel;
        try {
            
            contractModel = RentConverter.toContractEntity(rent, file);
        } catch (Exception e) {
            throw new BadRequestException("Failed to save image" + e.getMessage());
        }
        rent.setImage_contract(contractModel.getImage_contract());
        rent = rentRepository.save(rent);
        
        return RentConverter.toContractModel(rent);
    }


}
