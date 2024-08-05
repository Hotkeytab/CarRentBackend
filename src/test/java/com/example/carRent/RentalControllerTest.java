package com.example.carRent;



import com.example.carRent.Auth.Entity.User;
import com.example.carRent.Carmanagement.Entity.Car;
import com.example.carRent.Rentalmanagement.RentalController;
import com.example.carRent.Rentalmanagement.Entity.Rental;
import com.example.carRent.Rentalmanagement.Service.RentalService;
import com.example.carRent.Shared.Dto.RentalRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(value = RentalController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rental rental;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create sample Car and User objects
        Car car = new Car();
        car.setId(1L);

        User user = new User();
        user.setId(1L);
        
        // Create a sample rental for testing
        rental = new Rental();
        rental.setId(1L);
        rental.setCar(car);
        rental.setUser(user);
        rental.setStartDate(LocalDate.now());
        rental.setEndDate(LocalDate.now().plusDays(1));
    }

    @Test
    public void testCreateRental() throws Exception {
        RentalRequest request = new RentalRequest();
        request.setCarId(1L);
        request.setUserId(1L);
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(1));

        when(rentalService.createRental(any(RentalRequest.class))).thenReturn(rental);

        mockMvc.perform(MockMvcRequestBuilders.post("/rentals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId").value(1L))  // Adjusted path for nested Car object
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1L)); // Adjusted path for nested User object
    }

    @Test
    public void testGetRentalById() throws Exception {
        when(rentalService.getRentalById(anyLong())).thenReturn(rental);

        mockMvc.perform(MockMvcRequestBuilders.get("/rentals/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1L));
    }

    @Test
    public void testUpdateRental() throws Exception {
        RentalRequest request = new RentalRequest();
        request.setCarId(1L);
        request.setUserId(1L);
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(1));

        when(rentalService.updateRental(anyLong(), any(RentalRequest.class))).thenReturn(rental);

        mockMvc.perform(MockMvcRequestBuilders.put("/rentals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1L));
    }

    @Test
    public void testDeleteRental() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/rentals/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}