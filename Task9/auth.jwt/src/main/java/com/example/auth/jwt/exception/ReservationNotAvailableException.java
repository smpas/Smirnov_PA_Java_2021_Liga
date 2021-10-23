package com.example.auth.jwt.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationNotAvailableException extends RuntimeException {
    private LocalDateTime time;

    public ReservationNotAvailableException(LocalDateTime time) {
        super(String.format("Reservation in %s is not available", time.toString()));
        this.time = time;
    }
}


