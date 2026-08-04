package com.example.smartparking.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillResponse {

    private Long reservationId;

    private String registrationNumber;

    private String slotNumber;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private long durationHours;

    private Double amount;
}