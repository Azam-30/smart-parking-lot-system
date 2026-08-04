package com.example.smartparking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smartparking.entity.Reservation;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {
	
	List<Reservation> findByUserUsername(
	        String username);

}