package com.example.smartparking.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationResponse {

    private Long reservationId;

    private String slotNumber;

    private LocalDateTime reservationTime;

    private String status;
}