package com.example.smartparking.service;

import com.example.smartparking.dto.ReservationRequest;
import com.example.smartparking.dto.ReservationResponse;

public interface ReservationService {

    ReservationResponse reserveSlot(
            ReservationRequest request);

    void markEntry(Long reservationId);

    void markExit(Long reservationId);

    ReservationResponse getReservation(Long reservationId);

    void cancelReservation(Long reservationId);
}