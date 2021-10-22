package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.NewReservationDTO;
import com.example.auth.jwt.dto.ReservationDTO;
import com.example.auth.jwt.entity.Reservation;
import com.example.auth.jwt.entity.ReservationStatus;
import com.example.auth.jwt.entity.User;
import com.example.auth.jwt.exception.EntityNotFoundException;
import com.example.auth.jwt.repository.ReservationRepository;
import com.example.auth.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    private final LocalTime opening = LocalTime.parse("10:00");
    private final LocalTime closing = LocalTime.parse("23:00");
    private final Integer interval = 20;

    @Override
    public List<LocalTime> getSchedule(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, format);
        List<LocalTime> schedule = new ArrayList<>();

        if (localDate.isBefore(LocalDate.now())) {
            return schedule;
        }

        LocalTime temp = opening;
        if (localDate.equals(LocalDate.now())) {
            LocalTime now = LocalTime.now();
            while (temp.isBefore(closing)) {
                if (temp.isAfter(now)) {
                    schedule.add(temp);
                }
                temp = temp.plusMinutes(interval);
            }
            return schedule;
        }

        if (localDate.isAfter(LocalDate.now())) {
            while (temp.isBefore(closing)) {
                schedule.add(temp);
                temp = temp.plusMinutes(interval);
            }
        }
        return schedule;
    }

    @Override
    @Transactional
    public ReservationDTO makeReservation(NewReservationDTO reservationDTO) {
        User user = userRepository.findById(reservationDTO.getUser())
                        .orElseThrow(() -> new EntityNotFoundException(User.class.getName(), reservationDTO.getUser()));

        Reservation newReservation =
                reservationRepository.save(new Reservation(null, user, reservationDTO.getTime(), ReservationStatus.NEW));
        return new ReservationDTO(newReservation.getId(), newReservation.getUser().getId(),
                newReservation.getTime(), newReservation.getStatus());
    }
}
