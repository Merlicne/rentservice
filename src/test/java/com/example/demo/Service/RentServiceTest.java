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
import com.example.demo.model.JwtToken;
import com.example.demo.model.RentModel;
import com.example.demo.model.Role;
import com.example.demo.model.RoomModel;
import com.example.demo.repository.RentRepository;
import com.example.demo.repository.TenantRepository;
import com.example.demo.service.implement.RentService;
import com.example.demo.util.converter.TenantConverter;
import com.example.demo.webClient.IRoomService;


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

    @InjectMocks
    private RentService rentService;

    private JwtToken token;
    private Rent rent;
    private Tenant tenant;
    private RentModel rentModel;
    private RoomModel roomModel;
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() throws IOException {
        String path = "src\\test\\java\\com\\example\\demo\\df09d2098b1b3909e70578b2b8ea7905.jpg";
        String fname = "df09d2098b1b3909e70578b2b8ea7905.jpg";
        String contentType = "image/jpeg";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        File file = new File(path);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        
        multipartFile = new MockMultipartFile(fname, fileContent);

        token = JwtToken.builder()
                .token("token")
                .build();
        tenant = Tenant.builder()
                .Id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .phoneNum("1234567890")
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
                .build();

        rentModel = RentModel.builder()
                .rentId(rent.getRent_id().toString())
                .period(rent.getPeriod())
                .startDate(rent.getStart_date().format(formatter))
                .dateOut(rent.getStart_date().format(formatter))
                .room(roomModel)
                .tenant(TenantConverter.toTenantModel(tenant))
                .room(roomModel)
                .imageContract(fileContent)
                .createdAt(rent.getCreatedAt())
                .updatedAt(rent.getUpdatedAt())
                .deletedAt(null)
                .build();
    }

    @Test
    void testGetAllRents() {
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(rentRepository.findAllRents()).thenReturn(List.of(rent));
        when(tenantRepository.findTenantById(any(UUID.class))).thenReturn(Optional.of(tenant));
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
        when(tenantRepository.findTenantById(any(UUID.class))).thenReturn(Optional.of(tenant));
        when(roomService.getRoom(anyInt(), any(JwtToken.class))).thenReturn(roomModel);

        RentModel result = rentService.getRentById(rentId.toString(), token);

        assertNotNull(result);
        verify(rentRepository, times(1)).findRentById(rentId);
    }

    @Test
    void testSaveRent() {
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);
        when(roomService.getRoom(anyInt(), any(JwtToken.class))).thenReturn(roomModel);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        RentModel result = rentService.saveRent(rentModel, multipartFile, token);

        assertNotNull(result);
        verify(rentRepository, times(1)).save(any(Rent.class));
    }

    @Test
    void testUpdateRent() {
        UUID rentId = UUID.randomUUID();
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);
        when(rentRepository.findRentById(any(UUID.class))).thenReturn(Optional.of(rent));
        when(tenantRepository.findTenantById(any(UUID.class))).thenReturn(Optional.of(tenant));
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);
        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);
        when(roomService.getRoom(anyInt(), any(JwtToken.class))).thenReturn(roomModel);

        RentModel result = rentService.updateRent(rentId.toString(), rentModel, multipartFile, token);

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

}
