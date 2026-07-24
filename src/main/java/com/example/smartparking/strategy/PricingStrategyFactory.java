package com.example.smartparking.strategy;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PricingStrategyFactory {

    private final NormalPricingStrategy normalPricingStrategy;

    private final PeakHourPricingStrategy peakHourPricingStrategy;

    public PricingStrategy getPricingStrategy() {

        int currentHour = LocalTime.now().getHour();

        if (currentHour >= 17 && currentHour <= 22) {
            return peakHourPricingStrategy;
        }

        return normalPricingStrategy;
    }
}