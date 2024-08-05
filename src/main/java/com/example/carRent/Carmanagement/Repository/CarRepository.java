package com.example.carRent.Carmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.carRent.Carmanagement.Entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    // Custom query methods can be added here if needed
}