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

        if(reservation.getEntryTime() == null){

            throw new IllegalStateException(
                    "Vehicle has not entered parking");
        }

        if(reservation.getExitTime() == null){

            throw new IllegalStateException(
                    "Vehicle has not exited parking");
        }

        long hours =
                Duration.between(
                        reservation.getEntryTime(),
                        reservation.getExitTime())
                        .toHours();

        if(hours <= 0){
            hours = 1;
        }

        PricingStrategy strategy =
                pricingStrategyFactory
                        .getPricingStrategy();

        double amount =
                strategy.calculatePrice(
                        hours);

        reservation.setBillAmount(amount);

        reservationRepository.save(
                reservation);

        return amount;
    }
}