package com.example.auth.jwt.exception;

import com.example.auth.jwt.entity.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncorrectReservationStatusException extends RuntimeException {
    private ReservationStatus necessaryStatus;
    private ReservationStatus currentStatus;

    public IncorrectReservationStatusException(ReservationStatus necessaryStatus, ReservationStatus currentStatus) {
        super(String.format("Reservation status must be %s, current status is %s", necessaryStatus, currentStatus));
        this.necessaryStatus = necessaryStatus;
        this.currentStatus = currentStatus;
    }
}
