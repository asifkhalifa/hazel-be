package com.hazelngrazel.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hazelngrazel.app.entity.ProfileEntity;
import com.hazelngrazel.app.entity.Role;
import com.hazelngrazel.app.repository.UserRepository;

@SpringBootApplication
public class HazelNGrazelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HazelNGrazelApplication.class, args);
	}

	@Bean
	public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByEmailIgnoreCase("admin@gmail.com").isEmpty()) {
				ProfileEntity admin = new ProfileEntity();
				admin.setName("Admin User");
				admin.setEmail("admin@gmail.com");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRole(Role.ROLE_ADMIN);
				userRepository.save(admin);
				System.out.println("Default admin user created: admin@gmail.com / admin123");
			}
		};
	}
}
