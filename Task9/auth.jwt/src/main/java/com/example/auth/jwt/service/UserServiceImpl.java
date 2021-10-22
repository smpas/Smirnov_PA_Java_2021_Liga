package com.example.auth.jwt.service;

import com.example.auth.jwt.dto.UserDTO;
import com.example.auth.jwt.dto.UserRegistrationDTO;
import com.example.auth.jwt.entity.Role;
import com.example.auth.jwt.entity.User;
import com.example.auth.jwt.exception.RoleNotFoundException;
import com.example.auth.jwt.repository.RoleRepository;
import com.example.auth.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserDTO saveUser(UserRegistrationDTO user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = Optional.ofNullable(roleRepository.findRoleByName(user.getRole()))
                .orElseThrow(() -> new RoleNotFoundException(user.getRole()));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        newUser.setRoles(roles);
        newUser = userRepository.save(newUser);

        return new UserDTO(newUser.getId(), newUser.getName(), newUser.getUsername());
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
