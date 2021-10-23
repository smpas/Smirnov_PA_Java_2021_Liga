package com.example.auth.jwt.repository;

import com.example.auth.jwt.entity.Reservation;
import com.example.auth.jwt.entity.ReservationStatus;
import com.example.auth.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserAndStatus(User user, ReservationStatus status);

    Reservation findByTime(LocalDateTime time);

    List<Reservation> findAllByTimeBetween(LocalDateTime time1, LocalDateTime time2);

    Reservation findFirstByStatusAndTimeAfter(ReservationStatus status, LocalDateTime time);
}
