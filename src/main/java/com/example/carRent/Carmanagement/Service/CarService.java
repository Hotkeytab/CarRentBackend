package com.example.carRent.Carmanagement.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.carRent.Carmanagement.Entity.Car;
import com.example.carRent.Carmanagement.Repository.CarRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // Create or Update a Car
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    // Get a Car by ID
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    // Get all Cars
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Delete a Car by ID
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
