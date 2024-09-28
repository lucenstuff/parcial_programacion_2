package com.example.parcial.parcial.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		return authService.login(request);
	}

	@PostMapping("register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		return authService.register(request);
	}
}
