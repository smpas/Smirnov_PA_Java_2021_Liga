package com.example.auth.jwt;

import com.example.auth.jwt.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final ReservationService reservationService;

    //@Scheduled(cron = "0 0/20 10-20 * * *")
    @Scheduled(fixedDelay = 30000)
    public void checkTimeoutReservations() {
        reservationService.checkTimeoutReservations();
    }

    @Scheduled(fixedDelay = 30000)
    public void checkLinkConfirming() {
        reservationService.checkUnconfirmedLinks();
    }
}
