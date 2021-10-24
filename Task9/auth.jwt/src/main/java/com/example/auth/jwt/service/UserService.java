package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.UserDTO;
import com.example.auth.jwt.dto.UserRegistrationDTO;
import com.example.auth.jwt.entity.Role;

public interface UserService {
    UserDTO saveUser(UserRegistrationDTO user);

    Role saveRole(Role role);

    Long getUserIdByUsername(String username);
}
