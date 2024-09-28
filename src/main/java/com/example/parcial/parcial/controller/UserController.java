package com.example.parcial.parcial.controller;

import com.example.parcial.parcial.model.User;
import com.example.parcial.parcial.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This class creates a REST API to manage users using the methods from the service
// and exposing the endpoints to perform CRUD operations.

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
