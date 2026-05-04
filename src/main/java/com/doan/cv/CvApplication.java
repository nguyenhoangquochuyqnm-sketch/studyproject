package com.doan.cv;

import com.doan.cv.entity.User;
import com.doan.cv.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class CvApplication {

	public static void main(String[] args) {
		SpringApplication.run(CvApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		return args -> {
//			if (userRepository.count() == 0) {
//				User admin = new User();
//				admin.setName("admin");
//				admin.setEmail("admin@gmail.com");
//				admin.setPassword(passwordEncoder.encode("12345"));
//
//				User regular = new User();
//				regular.setName("huy");
//				regular.setEmail("huy@gmail.com");
//				regular.setPassword(passwordEncoder.encode("12345"));
//
//				User Hanh = new User();
//				Hanh.setName("hanh");
//				Hanh.setEmail("hanh@gmail.com");
//				Hanh.setPassword(passwordEncoder.encode("12345"));
//
//				userRepository.saveAll(List.of(admin, regular, Hanh));
//				System.out.println("Seeded initial users.");
//			}
//		};
//	}
}
