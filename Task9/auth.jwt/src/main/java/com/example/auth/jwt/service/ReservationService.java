package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.ReservationDTO;
import com.example.auth.jwt.dto.ShortReservationDTO;

import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    List<LocalTime> getSchedule(String date);

    ReservationDTO makeReservation(ShortReservationDTO reservationDTO);

    List<ShortReservationDTO> getActiveReservations(Long userId);

    ReservationDTO getClosestReservation();

    ReservationDTO markReservationAsArrived(Long reservationId);

    ReservationDTO markReservationAsDone(Long reservationId);

    ReservationDTO markReservationAsCancelled(Long reservationId);
}
