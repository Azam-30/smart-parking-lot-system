package com.example.smartparking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}