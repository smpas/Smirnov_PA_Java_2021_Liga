package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.ReservationDTO;
import com.example.auth.jwt.dto.ShortReservationDTO;
import com.example.auth.jwt.entity.Reservation;
import com.example.auth.jwt.entity.ReservationStatus;
import com.example.auth.jwt.entity.User;
import com.example.auth.jwt.exception.EntityNotFoundException;
import com.example.auth.jwt.exception.ReservationNotAvailableException;
import com.example.auth.jwt.repository.ReservationRepository;
import com.example.auth.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        LocalDateTime start = localDate.atTime(opening);
        LocalDateTime end = localDate.atTime(closing);
        List<Reservation> reservations = reservationRepository.findAllByTimeBetween(start, end);

        List<LocalTime> times = reservations.stream().map(
                reservation -> reservation.getTime().toLocalTime()).collect(Collectors.toList());

        LocalTime temp = opening;
        if (localDate.equals(LocalDate.now())) {
            LocalTime now = LocalTime.now();
            while (temp.isBefore(closing)) {
                if (temp.isAfter(now) && !times.contains(temp)) {
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
    public ReservationDTO makeReservation(ShortReservationDTO reservationDTO) {
        User user = userRepository.findById(reservationDTO.getUser())
                .orElseThrow(() -> new EntityNotFoundException(User.class.getName(), reservationDTO.getUser()));

        LocalDateTime time = reservationDTO.getTime();
        if (reservationRepository.findByTime(time) != null || time.getMinute() % 20 != 0
                || time.toLocalTime().isBefore(opening) || time.toLocalTime().isAfter(closing.minusMinutes(1))) {
            throw new ReservationNotAvailableException(reservationDTO.getTime());
        }

        Reservation newReservation =
                reservationRepository.save(new Reservation(null, user, reservationDTO.getTime(), ReservationStatus.NEW));
        return new ReservationDTO(newReservation.getId(), newReservation.getUser().getId(),
                newReservation.getTime(), newReservation.getStatus());
    }

    @Override
    public List<ShortReservationDTO> getActiveReservations(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getName(), userId));

        return reservationRepository.findAllByUserAndStatus(user, ReservationStatus.NEW).stream()
                .map(this::convertReservationToShortDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getClosestReservation() {
        return Optional.ofNullable(reservationRepository.findFirstByStatusAndTimeAfter(ReservationStatus.NEW, LocalDateTime.now()))
                .map(this::convertReservationToDTO)
                .orElseThrow(() -> new RuntimeException("No new reservations"));
    }

    @Override
    @Transactional
    public ReservationDTO markReservationAsArrived(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class.getName(), reservationId)); //TODO: проверка статуса на new

        reservation.setStatus(ReservationStatus.ARRIVED);
        return convertReservationToDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationDTO markReservationAsDone(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class.getName(), reservationId)); //TODO: проверка статуса на arrived

        reservation.setStatus(ReservationStatus.DONE);
        return convertReservationToDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationDTO markReservationAsCancelled(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class.getName(), reservationId)); //TODO: проверка статуса на new

        reservation.setStatus(ReservationStatus.CANCELED);
        return convertReservationToDTO(reservationRepository.save(reservation));
    }

    private ShortReservationDTO convertReservationToShortDTO(Reservation reservation) {
        return new ShortReservationDTO(reservation.getUser().getId(), reservation.getTime());
    }

    private ReservationDTO convertReservationToDTO(Reservation reservation) {
        return new ReservationDTO(reservation.getId(), reservation.getUser().getId(),
                reservation.getTime(), reservation.getStatus());
    }
}
