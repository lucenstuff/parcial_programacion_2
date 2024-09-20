package com.example.parcial.parcial.service;

import com.example.parcial.parcial.model.User;
import com.example.parcial.parcial.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByEmail(email);
		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		List<SimpleGrantedAuthority> authorities =
				Stream.of(user.getRoles().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.authorities(authorities)
				.build();
	}
}
