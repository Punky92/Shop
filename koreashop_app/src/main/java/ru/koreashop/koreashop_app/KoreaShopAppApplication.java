package ru.koreashop.koreashop_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KoreaShopAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoreaShopAppApplication.class, args);
	}
}
