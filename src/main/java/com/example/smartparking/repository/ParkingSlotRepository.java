package com.example.smartparking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smartparking.entity.ParkingSlot;
import com.example.smartparking.enums.VehicleType;

public interface ParkingSlotRepository
        extends JpaRepository<ParkingSlot, Long> {

    List<ParkingSlot> findByOccupiedFalseAndVehicleType(
            VehicleType vehicleType);

    List<ParkingSlot>
    findByOccupiedFalseAndVehicleTypeAndEvChargingSupported(
            VehicleType vehicleType,
            boolean evChargingSupported);
}