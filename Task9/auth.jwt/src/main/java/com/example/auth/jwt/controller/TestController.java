package com.example.auth.jwt.controller;

import com.example.auth.jwt.dto.UserDTO;
import com.example.auth.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
Тестовый контроллер для проверки работоспособности аутенфикации
 */
@RestController
@RequiredArgsConstructor
public class TestController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/admin")
    public String admin() {
        return "hello, admin!";
    }

    @GetMapping("/user")
    public String user() {
        return "hello, user!";
    }
}
