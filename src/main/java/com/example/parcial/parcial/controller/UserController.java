package com.example.parcial.parcial.controller;

import com.example.parcial.parcial.model.User;
import com.example.parcial.parcial.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping("/save")
	public ResponseEntity<User> save(@RequestBody User user) {
		User savedUser = userService.save(user);
		return ResponseEntity.ok(savedUser);
	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAll() {
		List<User> userList = userService.getAll();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		Optional<User> user = userService.getById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
		user.setId(id);
		User updateduser = userService.update(user);
		return ResponseEntity.ok(updateduser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		userService.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
