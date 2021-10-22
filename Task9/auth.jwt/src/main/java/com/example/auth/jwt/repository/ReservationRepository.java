package com.example.auth.jwt.repository;

import com.example.auth.jwt.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
