package com.ingweb.tp.AvailabilityService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Availability {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private String status;

	public Availability(Object o, LocalDate of, LocalTime of1, LocalTime of2, String available, long l) {

	}
}


@Repository
interface AvailabilityRepository extends JpaRepository<Availability, Long> {

}



@RestController
@RequestMapping("/availabilities")
class AvailabilityController {
	private AvailabilityRepository availabilityRepository;
	public AvailabilityController(AvailabilityRepository availabilityRepository) {
		this.availabilityRepository = availabilityRepository;
	}

	@PostMapping(value = "/")
	public Availability createAvailability(@RequestBody Availability availability) {
		return availabilityRepository.save(availability);
	}

	@PutMapping(value = "/{id}")
	public Availability updateAvailability(@PathVariable Long id, @RequestBody Availability availability) {
		availability.setId(id);
		return availabilityRepository.save(availability);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteAvailability(@PathVariable Long id) {
		availabilityRepository.deleteById(id);
	}

}

@SpringBootApplication
public class AvailabilityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvailabilityServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(AvailabilityRepository availabilityRepository) {
		return args -> {
			availabilityRepository.save(new Availability(null, LocalDate.of(2024, 10, 30), LocalTime.of(9, 0), LocalTime.of(12, 0), "Available", 1L));
			availabilityRepository.save(new Availability(null, LocalDate.of(2024, 10, 30), LocalTime.of(13, 0), LocalTime.of(17, 0), "Available", 2L));
			availabilityRepository.findAll().forEach(availability -> { System.out.println(availability.toString()); });
		};
	}

}
