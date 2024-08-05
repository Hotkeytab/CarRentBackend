package com.example.carRent;



import com.example.carRent.Carmanagement.CarController;
import com.example.carRent.Carmanagement.Entity.Car;
import com.example.carRent.Carmanagement.Service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

@WebMvcTest(value = CarController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateOrUpdateCar_Success() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setMake("Toyota");

        when(carService.saveCar(any(Car.class))).thenReturn(car);

        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Toyota"));
    }

    @Test
    public void testGetCarById_Success() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setMake("Toyota");

        when(carService.getCarById(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Toyota"));
    }

    @Test
    public void testGetAllCars_Success() throws Exception {
        Car car1 = new Car();
        Car car2 = new Car();
        when(carService.getAllCars()).thenReturn(List.of(car1, car2));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").isNotEmpty());
    }

    @Test
    public void testDeleteCar_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}