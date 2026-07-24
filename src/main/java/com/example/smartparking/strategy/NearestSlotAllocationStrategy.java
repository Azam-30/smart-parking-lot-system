package com.example.smartparking.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.smartparking.entity.ParkingSlot;
import com.example.smartparking.entity.Vehicle;
import com.example.smartparking.exception.ResourceNotFoundException;
import com.example.smartparking.repository.ParkingSlotRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NearestSlotAllocationStrategy implements SlotAllocationStrategy {

    private final ParkingSlotRepository parkingSlotRepository;

    @Override
    public ParkingSlot allocateSlot(Vehicle vehicle) {

        List<ParkingSlot> availableSlots;

        if (vehicle.isEv()) {

            availableSlots =
                    parkingSlotRepository
                    .findByOccupiedFalseAndVehicleTypeAndEvChargingSupported(
                            vehicle.getVehicleType(),
                            true);

        } else {

            availableSlots =
                    parkingSlotRepository
                    .findByOccupiedFalseAndVehicleType(
                            vehicle.getVehicleType());
        }

        return availableSlots.stream()
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No parking slot available"));
    }
}