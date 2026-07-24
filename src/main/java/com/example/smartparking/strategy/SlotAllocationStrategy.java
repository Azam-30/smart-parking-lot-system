package com.example.smartparking.strategy;

import com.example.smartparking.entity.ParkingSlot;
import com.example.smartparking.entity.Vehicle;

public interface SlotAllocationStrategy {

    ParkingSlot allocateSlot(Vehicle vehicle);
}