package com.example.auth.jwt.controller;

import com.example.auth.jwt.dto.UserDTO;
import com.example.auth.jwt.dto.UserRegistrationDTO;
import com.example.auth.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/register")
    public UserDTO registerNewUser(@RequestBody UserRegistrationDTO user) {
        return userService.saveUser(user);
    }
}
