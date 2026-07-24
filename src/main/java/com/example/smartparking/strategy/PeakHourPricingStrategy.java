package com.example.smartparking.strategy;

import org.springframework.stereotype.Component;

@Component
public class PeakHourPricingStrategy implements PricingStrategy {

    private static final double RATE_PER_HOUR = 100.0;

    @Override
    public double calculatePrice(long hours) {

        if (hours <= 0) {
            hours = 1;
        }

        return hours * RATE_PER_HOUR;
    }
}