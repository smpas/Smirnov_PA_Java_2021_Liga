package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.UserDTO;
import com.example.auth.jwt.dto.UserRegistrationDTO;
import com.example.auth.jwt.entity.Role;
import com.example.auth.jwt.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface UserService {
    UserDTO saveUser(UserRegistrationDTO user);
    Role saveRole(Role role);
    Long getUserIdByUsername(String username);
}
