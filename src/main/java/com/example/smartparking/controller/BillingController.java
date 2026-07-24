package com.example.smartparking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartparking.service.BillingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/{reservationId}")
    public ResponseEntity<Double> generateBill(
            @PathVariable Long reservationId) {

        return ResponseEntity.ok(
                billingService.generateBill(
                        reservationId));
    }
}