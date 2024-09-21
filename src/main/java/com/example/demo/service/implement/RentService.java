package com.example.demo.service.implement;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.example.demo.entity.Rent;
import com.example.demo.entity.Tenant;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.middleware.JwtService;
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

    public RentModel saveRent(RentModel rentRequest, MultipartFile file, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        // validate
        RentValidator.validateRent(rentRequest);
        TenantValidator.validateTenant(rentRequest);

        Rent rent;
        try {
            rent = RentConverter.toRentEntity(rentRequest, file);
        } catch (Exception e) {
            throw new BadRequestException("Failed to save image" + e.getMessage());
        }
            Tenant tenant = TenantConverter.toTenantEntity(rentRequest);

            RoomModel room = roomService.getRoom(rentRequest.getRoom().getRoomID(), token);

            tenant.setToken(TenantTokenGenerator.generateToken(tenant.getPhoneNum()));
            tenant.setPassword(passwordEncoder.encode(tenant.getPhoneNum()));

            tenant.setRole(Role.TENANT);

            tenant = tenantRepository.save(tenant);

            rent.setTenant(tenant);

            rent = rentRepository.save(rent);

            return RentConverter.toRentModel(rent, tenant, room);
    }

    public RentModel updateRent(String rent_id, RentModel rentRequest, MultipartFile file, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        // rent
        UUID rent_uuid = UUID.fromString(rent_id);
        Rent rent = rentRepository.findRentById(rent_uuid).orElseThrow(() -> new NotFoundException("Rent not found"));

        RentValidator.validateRent(rentRequest);
        Rent new_rent;
        try {
             new_rent = RentConverter.toRentEntity(rentRequest, file);
        } catch (Exception e) {
            throw new BadRequestException("Failed to save image" + e.getMessage());
        }
            new_rent.setCreatedAt(rent.getCreatedAt());

            // tenant
            UUID tenant_uuid = rent.getTenant().getId();
            Tenant tenant = tenantRepository.findTenantById(tenant_uuid)
                    .orElseThrow(() -> new NotFoundException("Tenant not found"));

            TenantValidator.validateTenant(rentRequest);
            Tenant new_tenant = TenantConverter.toTenantEntity(rentRequest);
            new_tenant.setCreatedAt(tenant.getCreatedAt());
            new_tenant.setId(tenant_uuid);

            // save to db
            new_tenant = tenantRepository.save(new_tenant);
            new_rent.setTenant(new_tenant);
            new_rent = rentRepository.save(new_rent);
            RoomModel room = roomService.getRoom(rentRequest.getRoom().getRoomID(), token);
            return RentConverter.toRentModel(new_rent, new_tenant, room);
    }

    public void deleteRent(String id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        UUID uuid = UUID.fromString(id);
        Rent rent = rentRepository.findRentById(uuid).orElseThrow(() -> new NotFoundException("Rent not found"));

        rent.setDeletedAt(LocalDateTime.now());
        rentRepository.save(rent);
    }

    // public Iterable<RentResponse> getDeletedRents() {
    // List<Rent> rents = rentRepository.findDeletedRents();
    // return RentConverter.toRentModels(rents);
    // }

    // public RentResponse cancelRent(String id, CancelRentRequest
    // cancelRentRequest) {
    // UUID uuid = UUID.fromString(id);
    // Rent r = rentRepository.findRentById(uuid).orElseThrow(() -> new
    // NotFoundException("Rent not found"));

    // RentValidator.validateDateOut(cancelRentRequest);
    // LocalDateTime createdAt = r.getCreatedAt();

    // r.setDateOut(DateUtil.toLocalDate(cancelRentRequest.getDateOut()));
    // r.setCreatedAt(createdAt);
    // return RentConverter.toRentModel(rentRepository.save(r));

    // }

}
