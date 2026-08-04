package com.example.smartparking.service;

import com.example.smartparking.dto.BillResponse;

public interface BillingService {

	BillResponse generateBill(Long reservationId);
}