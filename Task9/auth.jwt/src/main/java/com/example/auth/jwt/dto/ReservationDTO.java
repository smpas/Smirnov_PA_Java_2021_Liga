package com.example.auth.jwt.dto;

import com.example.auth.jwt.entity.ReservationStatus;
import com.example.auth.jwt.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long user;
    private LocalDateTime time;
    private ReservationStatus status;
}
