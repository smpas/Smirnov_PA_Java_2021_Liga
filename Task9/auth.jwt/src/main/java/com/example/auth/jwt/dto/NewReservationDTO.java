package com.example.auth.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class NewReservationDTO {
    private Long user;
    private LocalDateTime time;
}
