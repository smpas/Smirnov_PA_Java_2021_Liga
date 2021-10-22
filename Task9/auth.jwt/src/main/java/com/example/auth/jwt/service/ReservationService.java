package com.example.auth.jwt.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    List<LocalTime> getSchedule(String date);
}
