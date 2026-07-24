package com.example.smartparking.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.example.smartparking.entity.Reservation;
import com.example.smartparking.exception.ResourceNotFoundException;
import com.example.smartparking.repository.ReservationRepository;
import com.example.smartparking.strategy.PricingStrategy;
import com.example.smartparking.strategy.PricingStrategyFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl
        implements BillingService {

    private final ReservationRepository reservationRepository;

    private final PricingStrategyFactory pricingStrategyFactory;

    @Override
    public Double generateBill(
            Long reservationId) {

        Reservation reservation =
                reservationRepository.findById(
                        reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reservation not found"));

        if (reservation.getEntryTime() == null
                || reservation.getExitTime() == null) {

            throw new IllegalStateException(
                    "Entry and exit must be completed");
        }

        long hours =
                Duration.between(
                        reservation.getEntryTime(),
                        reservation.getExitTime())
                        .toHours();

        PricingStrategy pricingStrategy =
                pricingStrategyFactory
                        .getPricingStrategy();

        double amount =
                pricingStrategy
                        .calculatePrice(hours);

        reservation.setBillAmount(amount);

        reservationRepository.save(
                reservation);

        return amount;
    }
}