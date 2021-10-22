package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.NewReservationDTO;
import com.example.auth.jwt.dto.ReservationDTO;

import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    List<LocalTime> getSchedule(String date);
    ReservationDTO makeReservation(NewReservationDTO reservationDTO);
}
