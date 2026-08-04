package com.example.smartparking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smartparking.dto.BillResponse;
import com.example.smartparking.service.BillingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/{reservationId}")
    public ResponseEntity<BillResponse>
    generateBill(
            @PathVariable Long reservationId){

        return ResponseEntity.ok(
                billingService.generateBill(
                        reservationId));
    }
}