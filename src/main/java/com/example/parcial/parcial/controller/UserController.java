package com.example.parcial.parcial.controller;

import com.example.parcial.parcial.model.User;
import com.example.parcial.parcial.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//En esta clase hago un API REST para gestionar los usuarios utilizando los metodos del service
//y exponiendo los endpoint para realizar las operaciones CRUD.

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	@PreAuthorize("hasRole('USER')")
  public User save(@RequestBody User user) {
		return userService.save(user);
  }

	@PostMapping("/login")
	@PreAuthorize("hasRole('USER')")
  public User login(@RequestBody User user) {
    return userService.save(user);
  }

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAll() {
		List<User> users = userService.getAll();
		return ResponseEntity.ok(users);
	}

}