package com.example.carRent.Rentalmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.carRent.Rentalmanagement.Entity.Rental;
import com.example.carRent.Rentalmanagement.Service.RentalService;
import com.example.carRent.Shared.Dto.RentalRequest;
import com.example.carRent.Shared.Dto.RentalResponse;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {
	
	
	

    @Autowired
    private RentalService rentalService;

    @PostMapping
    public ResponseEntity<RentalResponse> createRental(@RequestBody RentalRequest rentalRequest) {
        Rental rental = rentalService.createRental(rentalRequest);
        return new ResponseEntity<>(convertToResponse(rental), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Long id) {
        Rental rental = rentalService.getRentalById(id);
        return new ResponseEntity<>(convertToResponse(rental), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RentalResponse>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        List<RentalResponse> responses = rentals.stream()
                                                .map(this::convertToResponse)
                                                .toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> updateRental(@PathVariable Long id, @RequestBody RentalRequest rentalRequest) {
        Rental rental = rentalService.updateRental(id, rentalRequest);
        return new ResponseEntity<>(convertToResponse(rental), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private RentalResponse convertToResponse(Rental rental) {
        RentalResponse response = new RentalResponse();
        response.setId(rental.getId());
        response.setCarId(rental.getCar().getId());
        response.setUserId(rental.getUser().getId());
        response.setStartDate(rental.getStartDate());
        response.setEndDate(rental.getEndDate());
        return response;
    }
}