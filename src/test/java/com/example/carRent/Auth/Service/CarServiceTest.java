package com.example.carRent.Auth.Service;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.carRent.Carmanagement.Entity.Car;
import com.example.carRent.Carmanagement.Repository.CarRepository;
import com.example.carRent.Carmanagement.Service.CarService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCar() {
        Car car = new Car();
        car.setId(1L);
        car.setMake("Toyota");

        when(carRepository.save(car)).thenReturn(car);

        Car savedCar = carService.saveCar(car);
        assertNotNull(savedCar);
        assertEquals("Toyota", savedCar.getMake());
    }

    @Test
    public void testGetCarById() {
        Car car = new Car();
        car.setId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Optional<Car> foundCar = carService.getCarById(1L);
        assertTrue(foundCar.isPresent());
        assertEquals(1L, foundCar.get().getId());
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = List.of(new Car(), new Car());

        when(carRepository.findAll()).thenReturn(cars);

        List<Car> allCars = carService.getAllCars();
        assertNotNull(allCars);
        assertEquals(2, allCars.size());
    }

    @Test
    public void testDeleteCar() {
        doNothing().when(carRepository).deleteById(1L);

        carService.deleteCar(1L);
        verify(carRepository, times(1)).deleteById(1L);
    }
}