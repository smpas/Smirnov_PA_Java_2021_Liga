package com.example.auth.jwt;

import com.example.auth.jwt.dto.UserRegistrationDTO;
import com.example.auth.jwt.entity.Role;
import com.example.auth.jwt.repository.UserRepository;
import com.example.auth.jwt.service.ReservationService;
import com.example.auth.jwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserService userService, ReservationService reservationService, UserRepository userRepository) {
        return (args) -> {
            userService.saveRole(new Role(null, "ADMIN"));
            userService.saveRole(new Role(null, "USER"));

            userService.saveUser(new UserRegistrationDTO("pavel", "smpas", "1703", "ADMIN"));
            userService.saveUser(new UserRegistrationDTO("alexey", "smalex", "1804", "USER"));
        };
    }
}
