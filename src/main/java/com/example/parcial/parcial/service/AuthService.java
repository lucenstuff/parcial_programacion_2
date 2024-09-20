package com.example.parcial.parcial.Auth;

import com.example.parcial.parcial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	}

	// Add your service methods here
}
