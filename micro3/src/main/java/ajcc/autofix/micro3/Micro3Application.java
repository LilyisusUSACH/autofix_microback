package ajcc.autofix.micro3;

import ajcc.autofix.micro3.Entities.RegReparation;
import ajcc.autofix.micro3.Repositories.RegReparationRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Micro3Application {

	public static void main(String[] args) {
		SpringApplication.run(Micro3Application.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(RegReparationRepo regReparationRepo) {
		return args -> {

		};
	}
}
