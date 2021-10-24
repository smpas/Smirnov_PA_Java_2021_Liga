package com.example.auth.jwt.controller;

import com.example.auth.jwt.dto.ReservationDTO;
import com.example.auth.jwt.dto.ShortReservationDTO;
import com.example.auth.jwt.exception.Response;
import com.example.auth.jwt.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserReservationController {
    private final ReservationService reservationService;

    @GetMapping("/schedule/{date}")
    public List<LocalTime> getSchedule(@PathVariable String date) {
        return reservationService.getSchedule(date);
    }

    @PostMapping("/reservation")
    public ReservationDTO makeReservation(@RequestParam Long userId, @RequestParam String time) {
        return reservationService.makeReservation(new ShortReservationDTO(userId, LocalDateTime.parse(time))); // TODO: передавать пар-ры отдельно
    }

    @GetMapping("/reservation")
    public List<ShortReservationDTO> getActiveReservations(@RequestParam Long userId) {
        return reservationService.getActiveReservations(userId);
    }

    @GetMapping("/reservation/{id}/confirming")
    public Response confirmReservationOnLink(@PathVariable Long id) {
        return reservationService.confirmReservationOnLink(id);
    }
}
