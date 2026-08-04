package com.example.smartparking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartparking.concurrency.SlotLockManager;
import com.example.smartparking.dto.ReservationRequest;
import com.example.smartparking.dto.ReservationResponse;
import com.example.smartparking.entity.ParkingSlot;
import com.example.smartparking.entity.Reservation;
import com.example.smartparking.entity.User;
import com.example.smartparking.entity.Vehicle;
import com.example.smartparking.enums.ReservationStatus;
import com.example.smartparking.exception.ResourceNotFoundException;
import com.example.smartparking.repository.ParkingSlotRepository;
import com.example.smartparking.repository.ReservationRepository;
import com.example.smartparking.repository.UserRepository;
import com.example.smartparking.repository.VehicleRepository;
import com.example.smartparking.strategy.SlotAllocationStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl
        implements ReservationService {

    private final VehicleRepository vehicleRepository;

    private final ReservationRepository reservationRepository;

    private final ParkingSlotRepository parkingSlotRepository;

    private final UserRepository userRepository;

    private final SlotAllocationStrategy slotAllocationStrategy;

    private final SlotLockManager lockManager;

    @Override
    public ReservationResponse reserveSlot(
            ReservationRequest request) {

        lockManager.lock();

        try {

            User currentUser = getCurrentUser();

            Vehicle vehicle = vehicleRepository
                    .findByRegistrationNumber(
                            request.getRegistrationNumber())
                    .orElseGet(() ->
                            vehicleRepository.save(
                                    Vehicle.builder()
                                            .registrationNumber(
                                                    request.getRegistrationNumber())
                                            .vehicleType(
                                                    request.getVehicleType())
                                            .ev(request.isEv())
                                            .build()));

            ParkingSlot slot =
                    slotAllocationStrategy
                            .allocateSlot(vehicle);

            slot.setOccupied(true);

            parkingSlotRepository.save(slot);

            Reservation reservation =
                    Reservation.builder()
                            .user(currentUser)
                            .vehicle(vehicle)
                            .parkingSlot(slot)
                            .reservationTime(
                                    LocalDateTime.now())
                            .status(
                                    ReservationStatus.RESERVED)
                            .build();

            Reservation saved =
                    reservationRepository.save(
                            reservation);

            return mapToResponse(saved);

        } finally {

            lockManager.unlock();
        }
    }

    @Override
    public void markEntry(
            Long reservationId) {

        Reservation reservation =
                getAuthorizedReservation(
                        reservationId);

        if (reservation.getStatus()
                != ReservationStatus.RESERVED) {

            throw new IllegalStateException(
                    "Vehicle already entered or reservation invalid");
        }

        reservation.setEntryTime(
                LocalDateTime.now());

        reservation.setStatus(
                ReservationStatus.PARKED);

        reservationRepository.save(
                reservation);
    }

    @Override
    public void markExit(
            Long reservationId) {

        Reservation reservation =
                getAuthorizedReservation(
                        reservationId);

        if (reservation.getStatus()
                != ReservationStatus.PARKED) {

            throw new IllegalStateException(
                    "Vehicle has not entered parking");
        }

        reservation.setExitTime(
                LocalDateTime.now());

        reservation.setStatus(
                ReservationStatus.COMPLETED);

        ParkingSlot slot =
                reservation.getParkingSlot();

        slot.setOccupied(false);

        parkingSlotRepository.save(
                slot);

        reservationRepository.save(
                reservation);
    }

    @Override
    public ReservationResponse getReservation(
            Long reservationId) {

        Reservation reservation =
                getAuthorizedReservation(
                        reservationId);

        return mapToResponse(
                reservation);
    }

    @Override
    public void cancelReservation(
            Long reservationId) {

        Reservation reservation =
                getAuthorizedReservation(
                        reservationId);

        if (reservation.getStatus()
                == ReservationStatus.PARKED) {

            throw new IllegalStateException(
                    "Cannot cancel active parking");
        }

        ParkingSlot slot =
                reservation.getParkingSlot();

        slot.setOccupied(false);

        parkingSlotRepository.save(
                slot);

        reservation.setStatus(
                ReservationStatus.CANCELLED);

        reservationRepository.save(
                reservation);
    }

    @Override
    public List<ReservationResponse>
    getAllReservations() {

        if (isAdmin()) {

            return reservationRepository
                    .findAll()
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
        }

        return reservationRepository
                .findByUserUsername(
                        getLoggedInUsername())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Reservation getAuthorizedReservation(
            Long reservationId) {

        Reservation reservation =
                reservationRepository.findById(
                        reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reservation not found"));

        if (!isAdmin()) {

            validateOwnership(
                    reservation);
        }

        return reservation;
    }

    private void validateOwnership(
            Reservation reservation) {

        String loggedInUser =
                getLoggedInUsername();

        String owner =
                reservation.getUser()
                        .getUsername();

        if (!owner.equals(loggedInUser)) {

            throw new AccessDeniedException(
                    "You are not allowed to access this reservation");
        }
    }

    private User getCurrentUser() {

        return userRepository
                .findByUsername(
                        getLoggedInUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));
    }

    private String getLoggedInUsername() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return authentication.getName();
    }

    private boolean isAdmin() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority()
                                .equals("ROLE_ADMIN"));
    }

    private ReservationResponse mapToResponse(
            Reservation reservation) {

        return ReservationResponse.builder()

                .reservationId(
                        reservation.getId())

                .registrationNumber(
                        reservation.getVehicle()
                                .getRegistrationNumber())

                .slotNumber(
                        reservation.getParkingSlot()
                                .getSlotNumber())

                .username(
                        reservation.getUser()
                                .getUsername())

                .reservationTime(
                        reservation.getReservationTime())

                .status(
                        reservation.getStatus()
                                .name())

                .billAmount(
                        reservation.getBillAmount())

                .build();
    }
}