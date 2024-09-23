package com.example.demo.Service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entity.Rent;
import com.example.demo.entity.Tenant;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.ContractModel;
import com.example.demo.model.JwtToken;
import com.example.demo.model.RentModel;
import com.example.demo.model.Role;
import com.example.demo.model.RoomModel;
import com.example.demo.repository.RentRepository;
import com.example.demo.repository.TenantRepository;
import com.example.demo.service.implement.RentService;
import com.example.demo.util.converter.TenantConverter;
import com.example.demo.webClient.IRoomService;
import com.example.demo.enumurated.RoomStatus;




@ExtendWith(MockitoExtension.class)
class RentServiceTest {

    @Mock
    private RentRepository rentRepository;

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private IRoomService roomService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private RentService rentService;



    private JwtToken token;
    private Rent rent;
    private Tenant tenant;
    private RentModel rentModel;
    private RoomModel roomModel;
    private ContractModel contractModel;
    private byte[] fileContent;

    @BeforeEach
    void setUp() throws IOException {
        // Mock 
        fileContent = new byte[] { 1, 2, 3, 4, 5 }; 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        token = JwtToken.builder()
                .token("token")
                .build();
        tenant = Tenant.builder()
                .Id("ID")
                .firstName("John")
                .lastName("Doe")
                .phoneNum("1234567890")
                .password("password")
                .build();
        rent = Rent.builder()
                .rent_id(UUID.randomUUID())
                .room_id(1)
                .period(12)
                .start_date(LocalDate.parse("01-01-2021", formatter))
                .dateOut(LocalDate.parse("01-01-2021", formatter))
                .image_contract(fileContent)
                .tenant(tenant)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(null)
                .build();
        roomModel = RoomModel.builder()
                .roomID(1)
                .roomNo("101")
                .roomStatus(RoomStatus.NOT_RENTED)
                .build();

        rentModel = RentModel.builder()
                .rentId(rent.getRent_id().toString())
                .period(rent.getPeriod())
                .startDate(rent.getStart_date().format(formatter))
                .dateOut(rent.getStart_date().format(formatter))
                .room(roomModel)
                .tenant(TenantConverter.toTenantModel(tenant))
                .room(roomModel)
                // .imageContract(fileContent)
                .createdAt(rent.getCreatedAt())
                .updatedAt(rent.getUpdatedAt())
                .deletedAt(null)
                .build();
        contractModel = ContractModel.builder()
                .rent_id(rent.getRent_id().toString())
                .image_contract(fileContent)
                .build();
    }

    @Test
    void testGetAllRents(){
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(rentRepository.findAllRents()).thenReturn(List.of(rent));
        when(tenantRepository.findTenantById(anyString())).thenReturn(Optional.of(tenant));
        when(roomService.getRoom(1, token)).thenReturn(roomModel);

        Iterable<RentModel> rents = rentService.getAllRents(token);

        assertNotNull(rents);
        verify(rentRepository, times(1)).findAllRents();
    }

    @Test
    void testGetRentById() {
        UUID rentId = UUID.randomUUID();
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(rentRepository.findRentById(any(UUID.class))).thenReturn(Optional.of(rent));
        when(tenantRepository.findTenantById(anyString())).thenReturn(Optional.of(tenant));
        when(roomService.getRoom(anyInt(), any(JwtToken.class))).thenReturn(roomModel);

        RentModel result = rentService.getRentById(rentId.toString(), token);

        assertNotNull(result);
        verify(rentRepository, times(1)).findRentById(rentId);
    }

    @Test
    void testSaveRent()  {
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);
        when(roomService.getRoom(anyInt(), any(JwtToken.class))).thenReturn(roomModel);
        when(passwordEncoder.encode(tenant.getPhoneNum())).thenReturn("encodedPassword");
        when(roomService.updateRoom(roomModel.getRoomID(),roomModel, token)).thenReturn(roomModel);
        
        RentModel result = rentService.saveRent(rentModel, token);
        
        assertNotNull(result);
        verify(rentRepository, times(1)).save(any(Rent.class));
    }
    
    @Test
    void testUpdateRent(){
        UUID rentId = UUID.randomUUID();
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(rentRepository.findRentById(any(UUID.class))).thenReturn(Optional.of(rent));
        when(tenantRepository.findTenantById(anyString())).thenReturn(Optional.of(tenant));
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);
        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);
        when(roomService.getRoom(anyInt(), any(JwtToken.class))).thenReturn(roomModel);
        when(passwordEncoder.encode(tenant.getPhoneNum())).thenReturn("encodedPassword");

        RentModel result = rentService.updateRent(rentId.toString(), rentModel, token);

        assertNotNull(result);
        verify(rentRepository, times(1)).save(any(Rent.class));
    }

    @Test
    void testDeleteRent() {
        UUID rentId = UUID.randomUUID();
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(rentRepository.findRentById(any(UUID.class))).thenReturn(Optional.of(rent));

        rentService.deleteRent(rentId.toString(), token);

        verify(rentRepository, times(1)).save(any(Rent.class));
    }

    @Test
    void testSaveContract() throws IOException {
        // UUID rentId = UUID.randomUUID();
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(rentRepository.findRentById(any(UUID.class))).thenReturn(Optional.of(rent));
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);
        when(multipartFile.getBytes()).thenReturn(fileContent);


        ContractModel result = rentService.saveContract(rent.getRent_id().toString(), multipartFile, token);
        
        assertNotNull(result);
        assertEquals(contractModel, result);
    }

    @Test
    void testGetContract() {
        // UUID rentId = UUID.randomUUID();
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(rentRepository.findRentById(any(UUID.class))).thenReturn(Optional.of(rent));

        ContractModel result = rentService.getContract(rent.getRent_id().toString(), token);

        assertNotNull(result);
        assertEquals(contractModel, result);
    }

    @Test
    void testUpdateContract() throws IOException {
        // UUID rentId = UUID.randomUUID();
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(rentRepository.findRentById(any(UUID.class))).thenReturn(Optional.of(rent));
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);

        when(multipartFile.getBytes()).thenReturn(new byte[] { 1, 2, 3, 4, 5 });

        ContractModel result = rentService.updateContract(rent.getRent_id().toString(), multipartFile, token);

        assertNotNull(result);
        assertEquals(contractModel, result);
    }

}
