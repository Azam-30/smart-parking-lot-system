package com.example.smartparking.entity;

import java.time.LocalDateTime;

import com.example.smartparking.enums.ReservationStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private ParkingSlot parkingSlot;

    private LocalDateTime reservationTime;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Double billAmount;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}