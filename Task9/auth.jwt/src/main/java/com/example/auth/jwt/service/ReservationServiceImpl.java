package com.example.auth.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {
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
}
