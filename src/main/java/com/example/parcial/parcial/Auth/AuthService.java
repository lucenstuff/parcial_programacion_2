package com.example.parcial.parcial.Auth;

import com.example.parcial.parcial.Jwt.JwtService;
import com.example.parcial.parcial.model.Role;
import com.example.parcial.parcial.model.User;
import com.example.parcial.parcial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public ResponseEntity<AuthResponse> login(LoginRequest request) {
		try {
			User user = userRepository.findByEmail(request.getEmail())
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));

			if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(createAuthResponse(null, "Invalid password", "error", null));
			}

			String token = jwtService.getToken(user);
			Long expiresIn = jwtService.getExpiration(token).getTime();

			return ResponseEntity.ok(
					createAuthResponse(token, "User logged in successfully", "success", expiresIn));
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(createAuthResponse(null, e.getMessage(), "error", null));
		}
	}

	public ResponseEntity<AuthResponse> register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(
							createAuthResponse(null, "User with email " + request.getEmail() + " already exists",
									"error", null));
		}

		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.phone(request.getPhone())
				.role(Role.USER)
				.build();

		userRepository.save(user);
		String token = jwtService.getToken(user);
		Long expiresIn = jwtService.getExpiration(token).getTime();
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(createAuthResponse(token, "User registered successfully", "success", expiresIn));
	}

	private AuthResponse createAuthResponse(String token, String message, String status,
			Long expiresIn) {
		return AuthResponse.builder()
				.token(token)
				.message(message)
				.status(status)
				.expiresIn(expiresIn)
				.build();
	}
}
