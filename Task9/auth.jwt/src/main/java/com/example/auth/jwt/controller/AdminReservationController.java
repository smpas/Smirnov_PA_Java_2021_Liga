package com.example.auth.jwt.controller;

import com.example.auth.jwt.dto.ReservationDTO;
import com.example.auth.jwt.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminReservationController {
    private final ReservationService reservationService;

    @GetMapping("/reservation/closest")
    public ReservationDTO getClosestReservation() {
        return reservationService.getClosestReservation();
    }

    @PutMapping("/reservation/{id}/arrived")
    public ReservationDTO markReservationAsArrived(@PathVariable Long id) {
        return reservationService.markReservationAsArrived(id);
    }

    @PutMapping("/reservation/{id}/done")
    public ReservationDTO markReservationAsDone(@PathVariable Long id) {
        return reservationService.markReservationAsDone(id);
    }

    @PutMapping("/reservation/{id}/cancelled")
    public ReservationDTO cancelReservation(@PathVariable Long id) {
        return reservationService.markReservationAsCancelled(id);
    }
}
