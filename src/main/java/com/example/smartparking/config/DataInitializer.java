package com.example.smartparking.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.smartparking.entity.ParkingSlot;
import com.example.smartparking.enums.VehicleType;
import com.example.smartparking.repository.ParkingSlotRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ParkingSlotRepository parkingSlotRepository;

    @Override
    public void run(String... args) {

        if (parkingSlotRepository.count() == 0) {

            parkingSlotRepository.save(
                    ParkingSlot.builder()
                            .slotNumber("C1")
                            .vehicleType(VehicleType.CAR)
                            .occupied(false)
                            .evChargingSupported(false)
                            .build());

            parkingSlotRepository.save(
                    ParkingSlot.builder()
                            .slotNumber("C2")
                            .vehicleType(VehicleType.CAR)
                            .occupied(false)
                            .evChargingSupported(true)
                            .build());

            parkingSlotRepository.save(
                    ParkingSlot.builder()
                            .slotNumber("B1")
                            .vehicleType(VehicleType.BIKE)
                            .occupied(false)
                            .evChargingSupported(false)
                            .build());

            parkingSlotRepository.save(
                    ParkingSlot.builder()
                            .slotNumber("T1")
                            .vehicleType(VehicleType.TRUCK)
                            .occupied(false)
                            .evChargingSupported(false)
                            .build());

            System.out.println("Parking slots initialized.");
        }
    }
}