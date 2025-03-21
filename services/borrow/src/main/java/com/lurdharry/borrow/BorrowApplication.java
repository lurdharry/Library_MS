package com.lurdharry.borrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class BorrowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowApplication.class, args);
	}

}
