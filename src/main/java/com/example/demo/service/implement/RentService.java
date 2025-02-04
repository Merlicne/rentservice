package com.example.demo.service.implement;

import org.checkerframework.checker.units.qual.s;
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
import com.example.demo.model.BuildingModel;
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
import com.example.demo.webClient.IDormService;
import com.example.demo.webClient.IRoomService;

import com.example.demo.enumurated.RoomStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RentService implements IRentService {
    private final RentRepository rentRepository;
    private final TenantRepository tenantRepository;

    private final IRoomService roomService;
    // private final IDormService dormService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public List<RentModel> getAllRents(JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        List<Rent> rents = rentRepository.findAllRents();

        List<RentModel> rentModels = new ArrayList<>();
        for (Rent rent : rents) {
            Tenant tenant = tenantRepository.findTenantById(rent.getTenant().getId())
                    .orElseThrow(() -> new NotFoundException("Tenant not found"));
            RoomModel room = roomService.getRoom(rent.getRoom_id(), token).orElseThrow(() -> new NotFoundException("Room not found"));
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
        RoomModel room = roomService.getRoom(rent.getRoom_id(), token).orElseThrow(() -> new NotFoundException("Room not found"));
        return RentConverter.toRentModel(rent, tenant, room);
    }

    @Transactional
    public RentModel saveRent(RentModel rentRequest, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        // validate
        RentValidator.validateRent(rentRequest);
        
        Rent rent = RentConverter.toRentEntity(rentRequest);
        
        RoomModel room = roomService.getRoom(rentRequest.getRoom().getRoomID(), token).orElseThrow(() -> new NotFoundException("Room not found"));
        if(room.getRoomStatus().equals(RoomStatus.RENTED)) {
            throw new BadRequestException("Room is already rented");
        }
        
        // save tenant to repository
        Tenant tenant = TenantConverter.toTenantEntity(rentRequest);
        tenant.setId(TenantTokenGenerator.generateToken(tenant.getPhoneNum()));
        tenant.setPassword(passwordEncoder.encode(tenant.getPhoneNum()));
        tenant.setRole(Role.TENANT);
        TenantValidator.validateTenant(tenant);
        Tenant tenant_repo = tenantRepository.save(tenant);
        rent.setTenant(tenant_repo);
        rent = rentRepository.save(rent);
        room.setRoomStatus(RoomStatus.RENTED);
        roomService.updateRoom(room.getRoomID(),room, token);

        RentModel rentModel =  RentConverter.toRentModel(rent, tenant_repo, room);
        
        return rentModel;
    }

    @Transactional
    public RentModel updateRent(String rentId, RentModel rentRequest,  JwtToken token) {
        System.out.println("update rent");

        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        UUID rentUuid = UUID.fromString(rentId);
        Rent rent = rentRepository.findRentById(rentUuid).orElseThrow(() -> new NotFoundException("Rent not found"));

        RentValidator.validateRent(rentRequest);
        Rent newRent = RentConverter.toRentEntity(rentRequest);
        
        newRent.setCreatedAt(rent.getCreatedAt());

        String tenantUuid = rent.getTenant().getId();
        Tenant tenant = tenantRepository.findTenantById(tenantUuid).orElseThrow(() -> new NotFoundException("Tenant not found"));

        Tenant newTenant = TenantConverter.toTenantEntity(rentRequest);
        newTenant.setId(tenant.getId());
        newTenant.setPassword(passwordEncoder.encode(newTenant.getPhoneNum()));
        newTenant.setRole(Role.TENANT);
        newTenant.setCreatedAt(tenant.getCreatedAt());
        newTenant.setId(tenantUuid);
        TenantValidator.validateTenant(newTenant);


        newTenant = tenantRepository.save(newTenant);
        newRent.setTenant(newTenant);
        newRent = rentRepository.save(newRent);
        RoomModel room = null;
        if (newRent.getDateOut() != null && newRent.getDateOut().equals(rent.getDateOut())) {
            room = roomService.getRoom(rent.getRoom_id(), token).orElseThrow(() -> new NotFoundException("Room not found"));
            room.setRoomStatus(RoomStatus.NOT_RENTED);
            room = roomService.updateRoom(room.getRoomID(), room, token).orElseThrow(() -> new NotFoundException("Room not found"));
        }
        return RentConverter.toRentModel(newRent, newTenant, room);
    }

    @Transactional
    public void deleteRent(String id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        String username = jwtService.extractUsername(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        System.out.println("delete rent by "+ username);

        UUID uuid = UUID.fromString(id);
        Rent rent = rentRepository.findRentById(uuid).orElseThrow(() -> new NotFoundException("Rent not found"));
        RoomModel room = roomService.getRoom(rent.getRoom_id(), token).orElseThrow(() -> new NotFoundException("Room not found"));
        System.out.println("ROOM: "+room);
        System.out.println("RENT: "+rent);
        if (room.getRoomStatus().equals(RoomStatus.RENTED) && rent.getDateOut() == null) {
            room.setRoomStatus(RoomStatus.NOT_RENTED);
            roomService.updateRoom(room.getRoomID(), room, token);
        }
        rent.setDeletedAt(LocalDateTime.now());
        rentRepository.save(rent);
    }

// contract image management
    @Transactional
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
        Rent rent_ = rentRepository.save(rent);
        return RentConverter.toContractModel(rent_);
    }

    
    public ContractModel getContract(String rent_id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        UUID rentUuid = UUID.fromString(rent_id);
        Rent rent = rentRepository.findRentById(rentUuid).orElseThrow(() -> new NotFoundException("Rent not found"));

        return RentConverter.toContractModel(rent);
    }

    @Transactional
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

    public RentModel getRentByTenantId(String tenant_id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        Tenant tenant = tenantRepository.findById(tenant_id).orElseThrow(() -> new NotFoundException("Tenant not found"));
        Rent rent = rentRepository.findByTenantId(tenant.getId()).orElseThrow(() -> new NotFoundException("Rent not found"));
        RoomModel room = roomService.getRoom(rent.getRoom_id(), token).orElseThrow(() -> new NotFoundException("Room not found"));
        return RentConverter.toRentModel(rent, tenant, room);
    }

    public RentModel getRentByRoomId(int room_id, JwtToken token) {
        Role role = jwtService.extractRole(token.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        Rent rent = rentRepository.findByRoomId(room_id).orElseThrow(() -> new NotFoundException("Rent not found"));
        Tenant tenant = tenantRepository.findTenantById(rent.getTenant().getId()).orElseThrow(() -> new NotFoundException("Tenant not found"));
        RoomModel room = roomService.getRoom(rent.getRoom_id(), token).orElseThrow(() -> new NotFoundException("Room not found"));
        return RentConverter.toRentModel(rent, tenant, room);
    }


}
