package com.libraryExtra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //si hay problema, sacarlo.
public class LibraryExtraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryExtraApplication.class, args);
	}

}
