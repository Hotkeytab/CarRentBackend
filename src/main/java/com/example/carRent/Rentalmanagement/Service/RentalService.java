package com.example.carRent.Rentalmanagement.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.carRent.Auth.Entity.User;
import com.example.carRent.Auth.Repository.UserRepository;
import com.example.carRent.Carmanagement.Entity.Car;
import com.example.carRent.Carmanagement.Repository.CarRepository;
import com.example.carRent.Rentalmanagement.Entity.Rental;
import com.example.carRent.Rentalmanagement.Repository.RentalRepository;
import com.example.carRent.Shared.Dto.RentalRequest;

import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    public Rental createRental(RentalRequest rentalRequest) {
        Car car = carRepository.findById(rentalRequest.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        User user = userRepository.findById(rentalRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(user);
        rental.setStartDate(rentalRequest.getStartDate());
        rental.setEndDate(rentalRequest.getEndDate());

        return rentalRepository.save(rental);
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental updateRental(Long id, RentalRequest rentalRequest) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Car car = carRepository.findById(rentalRequest.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        User user = userRepository.findById(rentalRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        rental.setCar(car);
        rental.setUser(user);
        rental.setStartDate(rentalRequest.getStartDate());
        rental.setEndDate(rentalRequest.getEndDate());

        return rentalRepository.save(rental);
    }

    public void deleteRental(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new RuntimeException("Rental not found");
        }
        rentalRepository.deleteById(id);
    }
}