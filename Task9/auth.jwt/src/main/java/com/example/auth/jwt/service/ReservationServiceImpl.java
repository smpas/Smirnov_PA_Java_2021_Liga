package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.ReservationDTO;
import com.example.auth.jwt.dto.ShortReservationDTO;
import com.example.auth.jwt.entity.Reservation;
import com.example.auth.jwt.entity.ReservationStatus;
import com.example.auth.jwt.entity.User;
import com.example.auth.jwt.exception.EntityNotFoundException;
import com.example.auth.jwt.exception.IncorrectReservationStatusException;
import com.example.auth.jwt.exception.ReservationNotAvailableException;
import com.example.auth.jwt.exception.Response;
import com.example.auth.jwt.repository.ReservationRepository;
import com.example.auth.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Value("${app.opening}")
    private LocalTime openingHours;

    @Value("${app.closing}")
    private LocalTime closingHours;

    @Value("${app.intervalMinutes}")
    private Integer intervalMinutes;

    @Value("${app.linkExpirationTimeMinutes}")
    private Integer linkExpirationTimeMinutes;

    @Value("${app.host}")
    private String appHost;

    @Value("${app.port}")
    private String appPort;

    @Override
    public List<LocalTime> getSchedule(String date) {
        LocalDate scheduledDate = LocalDate.parse(date);
        List<LocalTime> schedule = new ArrayList<>();

        List<Reservation> reservations = reservationRepository.findAllByTimeBetween
                (LocalDateTime.of(scheduledDate, openingHours), LocalDateTime.of(scheduledDate, closingHours));

        List<LocalDateTime> reservedTimeSlots = reservations.stream()
                .map(Reservation::getTime)
                .collect(Collectors.toList());

        LocalDateTime iterationTime = LocalDateTime.of(scheduledDate, openingHours);
        LocalDateTime closingHoursOnScheduledDate = LocalDateTime.of(scheduledDate, closingHours);
        LocalDateTime now = LocalDateTime.now();
        while (iterationTime.isBefore(closingHoursOnScheduledDate)) {
            if (iterationTime.isAfter(now) && !reservedTimeSlots.contains(iterationTime)) {
                schedule.add(iterationTime.toLocalTime());
            }
            iterationTime = iterationTime.plusMinutes(intervalMinutes);
        }
        return schedule;
    }

    @Override
    @Transactional
    public ReservationDTO makeReservation(ShortReservationDTO reservationDTO) {
        User user = userRepository.findById(reservationDTO.getUser())
                .orElseThrow(() -> new EntityNotFoundException(User.class.getName(), reservationDTO.getUser()));

        LocalDateTime reservationTime = reservationDTO.getTime();
        validateReservationTime(reservationTime);
        Reservation savedReservation = reservationRepository.save(new Reservation(null, user,
                reservationTime, LocalDateTime.now(), ReservationStatus.UNCONFIRMED));

        StringBuilder messageWithLink = new StringBuilder("Your confirmation link: http://")
                .append(appHost).append(":").append(appPort).append("/user/reservation/")
                .append(savedReservation.getId()).append("/confirming").append("\nIt will be available only for ")
                .append(linkExpirationTimeMinutes).append(" minutes!");
        System.out.println(messageWithLink);

        return new ReservationDTO(savedReservation.getId(), savedReservation.getUser().getId(),
                savedReservation.getTime(), savedReservation.getStatus());
    }

    @Override
    public List<ShortReservationDTO> getActiveReservations(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getName(), userId));

        return reservationRepository.findAllByUserAndStatus(user, ReservationStatus.ACTIVE).stream()
                .map(this::convertReservationToShortDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getClosestReservation() {
        return Optional.ofNullable(reservationRepository.findFirstByStatusAndTimeAfter(ReservationStatus.ACTIVE,
                        LocalDateTime.now()))
                .map(this::convertReservationToDTO)
                .orElseThrow(() -> new RuntimeException("No new reservations"));
    }

    @Override
    @Transactional
    public ReservationDTO markReservationAsArrived(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class.getName(), reservationId));

        if (!reservation.getStatus().equals(ReservationStatus.ACTIVE)) {
            throw new IncorrectReservationStatusException(ReservationStatus.ACTIVE, reservation.getStatus());
        }

        reservation.setStatus(ReservationStatus.ARRIVED);
        return convertReservationToDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationDTO markReservationAsDone(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class.getName(), reservationId));

        if (!reservation.getStatus().equals(ReservationStatus.ARRIVED)) {
            throw new IncorrectReservationStatusException(ReservationStatus.ARRIVED, reservation.getStatus());
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        return convertReservationToDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationDTO markReservationAsCancelled(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class.getName(), reservationId));

        if (!reservation.getStatus().equals(ReservationStatus.ACTIVE)) {
            throw new IncorrectReservationStatusException(ReservationStatus.ACTIVE, reservation.getStatus());
        }

        reservation.setStatus(ReservationStatus.CANCELED);
        return convertReservationToDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public void checkTimeoutReservations() {
        List<Reservation> timeoutReservations = reservationRepository.findAllByStatusAndTimeBefore(ReservationStatus.ACTIVE,
                LocalDateTime.now().minusMinutes(intervalMinutes));

        timeoutReservations.forEach(r -> {
            r.setStatus(ReservationStatus.TIMEOUT);
            reservationRepository.save(r);
        });
    }

    @Override
    @Transactional
    public void checkUnconfirmedLinks() {
        List<Reservation> unconfirmedReservations =
                reservationRepository.findAllByStatusAndCreationTimeBefore(ReservationStatus.UNCONFIRMED,
                        LocalDateTime.now().minusMinutes(linkExpirationTimeMinutes));

        unconfirmedReservations.forEach(r -> r.setStatus(ReservationStatus.ANNULLED));
    }

    @Override
    @Transactional
    public Response confirmReservationOnLink(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException(Reservation.class.getName(), reservationId));

        ReservationStatus status = reservation.getStatus();
        switch (status) {
            case UNCONFIRMED:
                reservation.setStatus(ReservationStatus.ACTIVE);
                reservationRepository.save(reservation);
                return new Response("You successfully confirmed your reservation");
            case ANNULLED:
                return new Response("Your confirmation link expired");
            case ACTIVE:
                return new Response("Your reservation has already been confirmed");
            default:
                throw new RuntimeException("Bad status of reservation");
        }
    }

    private void validateReservationTime(LocalDateTime time) {
        Reservation existingReservation = reservationRepository.findByTime(time);
        if (time.isBefore(LocalDateTime.now())
                || (existingReservation != null && (existingReservation.getStatus().equals(ReservationStatus.ACTIVE)
                || existingReservation.getStatus().equals(ReservationStatus.UNCONFIRMED)))
                || time.getMinute() % intervalMinutes != 0
                || time.toLocalTime().isBefore(openingHours)
                || time.toLocalTime().isAfter(closingHours.minusMinutes(1))) {
            throw new ReservationNotAvailableException(time);
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
