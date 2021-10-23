package com.example.auth.jwt.controller;

import com.example.auth.jwt.dto.ReservationDTO;
import com.example.auth.jwt.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ReservationService reservationService;

    @GetMapping("/reservation/closest")
    public ReservationDTO getClosestReservation() {
        return reservationService.getClosestReservation();
    }

    @PutMapping("/reservation/{id}/arrived")
    public ReservationDTO markUserAsArrived(@PathVariable Long id) {
        return reservationService.markUserAsArrived(id);
    }
}
