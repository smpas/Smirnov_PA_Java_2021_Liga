package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.UserDTO;
import com.example.auth.jwt.entity.Role;
import com.example.auth.jwt.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    User getUser(String username);
    List<UserDTO> getUsers();
}
