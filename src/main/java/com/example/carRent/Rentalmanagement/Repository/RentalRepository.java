package com.example.carRent.Rentalmanagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carRent.Auth.Entity.User;
import com.example.carRent.Rentalmanagement.Entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUser(User user);
}