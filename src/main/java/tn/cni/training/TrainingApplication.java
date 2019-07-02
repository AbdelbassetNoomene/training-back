package tn.cni.training;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tn.cni.training.model.User;
import tn.cni.training.repository.UserRepository;

@SpringBootApplication
public class TrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingApplication.class, args);
	}

	@Bean
	CommandLineRunner start(UserRepository userRepository) {
		return args -> {
			userRepository.save(new User(1L,"test1@mail.com","test","Ahmed", "salah","22547874",null));
			userRepository.save(new User(2L,"test2@mail.com","test","Ali", "saidi","22547874",null));
			userRepository.save(new User(3L,"test3@mail.com","test","Ammar", "slimani","22547874",null));
			userRepository.save(new User(4L,"test4@mail.com","test","Belgacem", "swilah","22547874",null));
			userRepository.save(new User(5L,"test5@mail.com","test","Nizar", "beji","22547874",null));
			userRepository.save(new User(6L,"test6@mail.com","test","Anis", "saidani","22547874",null));
			userRepository.save(new User(7L,"test7@mail.com","test","Ameur", "salah","22547874",null));
			userRepository.save(new User(8L,"test8@mail.com","test","Jabeur", "trabelsi","22547874",null));
			
		};
	}

}
