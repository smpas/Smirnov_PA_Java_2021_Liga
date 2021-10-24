package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.ReservationDTO;
import com.example.auth.jwt.dto.ShortReservationDTO;
import com.example.auth.jwt.entity.Reservation;
import com.example.auth.jwt.entity.ReservationStatus;
import com.example.auth.jwt.entity.User;
import com.example.auth.jwt.exception.EntityNotFoundException;
import com.example.auth.jwt.exception.ReservationNotAvailableException;
import com.example.auth.jwt.exception.Response;
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

    private static final LocalTime OPENING_HOURS = LocalTime.parse("03:00");
    private static final LocalTime CLOSING_HOURS = LocalTime.parse("23:00");
    private static final Integer INTERVAL = 20;

    @Override
    public List<LocalTime> getSchedule(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate scheduledDate = LocalDate.parse(date, format);
        List<LocalTime> schedule = new ArrayList<>();

        if (scheduledDate.isBefore(LocalDate.now())) {
            return schedule;
        }

        LocalDateTime start = scheduledDate.atTime(OPENING_HOURS);
        LocalDateTime end = scheduledDate.atTime(CLOSING_HOURS);
        List<Reservation> reservations = reservationRepository.findAllByTimeBetween(start, end);

        List<LocalTime> reservedTimeSlots = reservations.stream().map(
                reservation -> reservation.getTime().toLocalTime()).collect(Collectors.toList());

        LocalTime iterationTime = OPENING_HOURS;
        if (scheduledDate.equals(LocalDate.now())) {
            LocalTime now = LocalTime.now();
            while (iterationTime.isBefore(CLOSING_HOURS)) {
                if (iterationTime.isAfter(now) && !reservedTimeSlots.contains(iterationTime)) {
                    schedule.add(iterationTime);
                }
                iterationTime = iterationTime.plusMinutes(INTERVAL);
            }
            return schedule;
        }

        if (scheduledDate.isAfter(LocalDate.now())) {
            while (iterationTime.isBefore(CLOSING_HOURS)) {
                schedule.add(iterationTime);
                iterationTime = iterationTime.plusMinutes(INTERVAL);
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
        Reservation existingReservation = reservationRepository.findByTime(time);
        if (time.isBefore(LocalDateTime.now())
                || (existingReservation != null
                && (existingReservation.getStatus().equals(ReservationStatus.NEW) ||
                existingReservation.getStatus().equals(ReservationStatus.UNCONFIRMED)))
                || time.getMinute() % 20 != 0
                || time.toLocalTime().isBefore(OPENING_HOURS)
                || time.toLocalTime().isAfter(CLOSING_HOURS.minusMinutes(1))) {
            throw new ReservationNotAvailableException(reservationDTO.getTime());
        }

        Reservation newReservation = reservationRepository.save
                (new Reservation(null, user, reservationDTO.getTime(), LocalDateTime.now(), ReservationStatus.UNCONFIRMED));
        System.out.println("Your confirmation link: http://localhost:8080/user/reservation/" + newReservation.getId() +
                "/confirming" + "\nIt will be available only for 15 minutes!");

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

    @Override
    @Transactional
    public void checkTimeoutReservations() {
        List<Reservation> timeoutReservations =
                reservationRepository.findAllByStatusAndTimeBefore(ReservationStatus.NEW,
                        LocalDateTime.now().minusMinutes(20));

        for (Reservation reservation : timeoutReservations) {
            reservation.setStatus(ReservationStatus.TIMEOUT);
            reservationRepository.save(reservation);
        }
    }

    @Override
    @Transactional
    public void checkUnconfirmedLinks() {
        List<Reservation> unconfirmedReservations =
                reservationRepository.findAllByStatusAndCreationTimeBefore(ReservationStatus.UNCONFIRMED,
                        LocalDateTime.now().minusMinutes(15));

        for (Reservation reservation : unconfirmedReservations) {
            reservation.setStatus(ReservationStatus.ANNULLED);
        }
    }

    @Override
    @Transactional
    public Response confirmReservationOnLink(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class.getName(), reservationId));

        ReservationStatus status = reservation.getStatus();
        switch (status) {
            case UNCONFIRMED:
                reservation.setStatus(ReservationStatus.NEW);
                reservationRepository.save(reservation);
                return new Response("You successfully confirmed your reservation");
            case ANNULLED:
                return new Response("Your confirmation link expired");
            case NEW:
                return new Response("Your reservation has already benn confirmed");
            default:
                throw new RuntimeException("Bad status of reservation");
        }
    }

    private ShortReservationDTO convertReservationToShortDTO(Reservation reservation) {
        return new ShortReservationDTO(reservation.getUser().getId(), reservation.getTime());
    }

    private ReservationDTO convertReservationToDTO(Reservation reservation) {
        return new ReservationDTO(reservation.getId(), reservation.getUser().getId(),
                reservation.getTime(), reservation.getStatus());
    }
}
