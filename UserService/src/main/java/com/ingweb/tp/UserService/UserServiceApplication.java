package com.ingweb.tp.UserService;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// Entity Classes
@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
class User {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
}

@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
class Client extends User {
	public Client(Object o, String janeDoe, String mail, String password123) {

	}
}


@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
class Professional extends User {
	private String specialty;

	public Professional(Object o, String s, String mail, String password123, String cardiologist) {

	}
}


///////////////////////////////////////////////////////////////////////////////////

// Repositories
@Repository
interface UserRepository extends JpaRepository<User, Long> {

}

@Repository interface ClientRepository extends JpaRepository<Client, Long> {

}

@Repository interface ProfessionalRepository extends JpaRepository<Professional, Long> {

}




@RestController @RequestMapping("/users")
class UserController {
	private UserRepository userRepository;
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping(value = "/")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PutMapping(value = "/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
		user.setId(id); return userRepository.save(user);
	}

	@DeleteMapping(value = "/{id}") public void deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
	}

}



@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}



	@Bean
	CommandLineRunner start(UserRepository userRepository, ProfessionalRepository professionalRepository, ClientRepository clientRepository) {
		return args -> {
			professionalRepository.save(new Professional(null, "Dr. John Doe", "john.doe@clinic.com", "password123", "Cardiologist"));
			professionalRepository.save(new Professional(null, "Dr. Emma Smith", "emma.smith@clinic.com", "password123", "Dentist"));
			clientRepository.save(new Client(null, "Jane Doe", "jane.doe@example.com", "password123"));
			clientRepository.save(new Client(null, "John Smith", "john.smith@example.com", "password123"));
			userRepository.findAll().forEach(user -> { System.out.println(user.toString()); }); }; }

}
