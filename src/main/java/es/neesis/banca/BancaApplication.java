package es.neesis.banca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "es.neesis.banca")
public class BancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancaApplication.class, args);
	}

}
