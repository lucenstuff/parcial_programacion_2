package com.example.parcial.parcial.service;

import com.example.parcial.parcial.model.User;
import com.example.parcial.parcial.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("User with this email already exists");
		}
		return userRepository.save(user);
	}


	public Optional<User> getById(Long id) {
		return userRepository.findById(id);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User update(User user) {
		if (!userRepository.existsById(user.getId())) {
			throw new IllegalArgumentException("User not found");
		}
		return userRepository.save(user);
	}

	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
}
