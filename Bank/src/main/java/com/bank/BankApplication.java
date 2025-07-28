package com.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
		"com.bank",
		"com.demo.exceptions",
		"com.demo.util.config",
		"com.bezkoder.spring.hibernate.manytomany.controller",
		"com.bezkoder.spring.hibernate.manytomany.repository"

	})
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);

	}
	

}
