package com.example.parcial.parcial.Auth;

import com.example.parcial.parcial.model.Role;
import com.example.parcial.parcial.model.User;
import com.example.parcial.parcial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
	private final AuthService authService;

	@Autowired
	private final UserRepository userRepository;
	
	//TODO: Check if user is already registered

	@PostMapping(value = "login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

		if (!userRepository.existsByEmail(request.getEmail())) {
			User user = User.builder()
					.email(request.getEmail())
					.password(request.getPassword())
					.firstName("")
					.lastName("")
					.phone("")
					.role(Role.USER)
					.build();
			userRepository.save(user);
		}
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping(value = "register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {

		return ResponseEntity.ok(authService.register(request));
	}


}