package com.example.smartparking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smartparking.dto.ReservationRequest;
import com.example.smartparking.dto.ReservationResponse;
import com.example.smartparking.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> reserveSlot(
            @Valid @RequestBody ReservationRequest request) {

        return ResponseEntity.ok(
                reservationService.reserveSlot(request));
    }

    @PutMapping("/{id}/entry")
    public ResponseEntity<String> markEntry(
            @PathVariable Long id) {

        reservationService.markEntry(id);

        return ResponseEntity.ok(
                "Vehicle entered successfully");
    }

    @PutMapping("/{id}/exit")
    public ResponseEntity<String> markExit(
            @PathVariable Long id) {

        reservationService.markExit(id);

        return ResponseEntity.ok(
                "Vehicle exited successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.getReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelReservation(
            @PathVariable Long id) {

        reservationService.cancelReservation(id);

        return ResponseEntity.ok(
                "Reservation cancelled successfully");
    }
    @GetMapping
    public ResponseEntity<List<ReservationResponse>>
    getAllReservations() {

        return ResponseEntity.ok(
                reservationService.getAllReservations()
        );
    }
}