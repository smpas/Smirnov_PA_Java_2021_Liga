package com.example.auth.jwt;

import com.example.auth.jwt.entity.Role;
import com.example.auth.jwt.entity.User;
import com.example.auth.jwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserService userService) {
		return (args) -> {
			Role role1 = userService.saveRole(new Role(null, "ADMIN"));
			Role role2 = userService.saveRole(new Role(null, "USER"));

			ArrayList<Role> roles1 = new ArrayList<>();
			roles1.add(role1);
			ArrayList<Role> roles2 = new ArrayList<>();
			roles2.add(role2);

			userService.saveUser(new User(null, "pavel", "smpas", "1703", roles1));
			userService.saveUser(new User(null, "alexey", "smalex", "1804", roles2));
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
