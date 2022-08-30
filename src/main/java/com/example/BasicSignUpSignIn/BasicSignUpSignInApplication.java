package com.example.BasicSignUpSignIn;

import com.example.BasicSignUpSignIn.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BasicSignUpSignInApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicSignUpSignInApplication.class, args);
	}

	@Bean
	public PasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
//	CommandLineRunner run(UserService userService){
//		return args -> {
//			userService.saveRole(new Role())
//		}
//	}
}

