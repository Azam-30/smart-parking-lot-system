package com.example.smartparking.dto;

import com.example.smartparking.enums.VehicleType;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReservationRequest {

    @NotBlank
    private String registrationNumber;

    private VehicleType vehicleType;

    private boolean ev;
}