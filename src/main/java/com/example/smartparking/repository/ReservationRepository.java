package com.example.smartparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smartparking.entity.Reservation;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

}