package com.example.auth.jwt;

import com.example.auth.jwt.dto.ShortReservationDTO;
import com.example.auth.jwt.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final ReservationService reservationService;

    @Scheduled(cron = "")
    public void fixedDelayTask() {
        reservationService.checkTimeoutReservations();
    }
}
