package com.example.smartparking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smartparking.entity.Vehicle;

public interface VehicleRepository
        extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByRegistrationNumber(
            String registrationNumber);
}