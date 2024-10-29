package com.ingweb.tp.AppointmentService;

import jakarta.persistence.*;
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
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Appointment {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private LocalTime time;
	private String status;

	public Appointment(Object o, LocalDate of, LocalTime of1, String scheduled, long l, long l1) {

	}
}

@Repository
interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}




@RestController
@RequestMapping("/appointments")
class AppointmentController {
	private AppointmentRepository appointmentRepository;
	public AppointmentController(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@PostMapping(value = "/")
	public Appointment createAppointment(@RequestBody Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	@PutMapping(value = "/{id}")
	public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
		appointment.setId(id);
		return appointmentRepository.save(appointment);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteAppointment(@PathVariable Long id) {
		appointmentRepository.deleteById(id);
	}

}




@SpringBootApplication
public class AppointmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner start(AppointmentRepository appointmentRepository) {
		return args -> {
			appointmentRepository.save(new Appointment(null, LocalDate.of(2024, 10, 30), LocalTime.of(10, 0), "Scheduled", 1L, 1L));
			appointmentRepository.save(new Appointment(null, LocalDate.of(2024, 10, 31), LocalTime.of(11, 0), "Scheduled", 2L, 1L));
			appointmentRepository.findAll().forEach(appointment -> { System.out.println(appointment.toString()); }); }; }

}
