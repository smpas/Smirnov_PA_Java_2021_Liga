package com.example.auth.jwt.controller;

import com.example.auth.jwt.dto.UserDTO;
import com.example.auth.jwt.dto.UserRegistrationDTO;
import com.example.auth.jwt.service.ReservationService;
import com.example.auth.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/*
Тестовый контроллер для проверки работоспособности аутенфикации
 */
@RestController
@RequiredArgsConstructor
public class TestController {
    private final UserService userService;
    private final ReservationService reservationService;

    @GetMapping("/admin")
    public String admin() {
        return "hello, admin!";
    }

    @GetMapping("/user")
    public String user() {
        return "hello, user!";
    }

    @PostMapping("/user/register")
    public UserDTO registerNewUser(@RequestBody UserRegistrationDTO user) {
        return userService.saveUser(user);
    }

    @GetMapping("/user/schedule/{date}")
    public List<LocalTime> getSchedule(@PathVariable String date) {
        return reservationService.getSchedule(date);
    }
}
