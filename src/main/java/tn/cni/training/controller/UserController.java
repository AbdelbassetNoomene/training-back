package tn.cni.training.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.cni.training.model.User;
import tn.cni.training.repository.UserRepository;


@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("")
	List<User>getListUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/{id}")
	User getUserDetails(@PathVariable long id) {
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? user.get() : null;
		
	}
	
	@PostMapping("")
	User addUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("")
	User updateUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@DeleteMapping("/{id}")
	void deleteUser(@PathVariable long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.delete(user.get());
		}
		
	}

}
