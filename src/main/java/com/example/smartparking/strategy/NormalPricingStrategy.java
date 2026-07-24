package com.example.smartparking.strategy;

import org.springframework.stereotype.Component;

@Component
public class NormalPricingStrategy implements PricingStrategy {

    private static final double RATE_PER_HOUR = 50.0;

    @Override
    public double calculatePrice(long hours) {

        if (hours <= 0) {
            hours = 1;
        }

        return hours * RATE_PER_HOUR;
    }
}