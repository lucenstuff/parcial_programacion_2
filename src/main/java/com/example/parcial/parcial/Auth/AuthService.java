package com.example.parcial.parcial.Auth;

import com.example.parcial.parcial.Jwt.JwtService;
import com.example.parcial.parcial.model.Role;
import com.example.parcial.parcial.model.User;
import com.example.parcial.parcial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		String token = jwtService.getToken((UserDetails) user);
		return AuthResponse.builder()
				.token(token)
				.build();
	}

	public AuthResponse register(RegisterRequest request) {
		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.role(Role.USER)
				.build();

		userRepository.save(user);

		return AuthResponse.builder()
				.token(jwtService.getToken((UserDetails) user))
				.build();
	}
}