package com.example.parcial.parcial.Auth;

import com.example.parcial.parcial.Jwt.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@Autowired
	private final TokenBlacklistService tokenBlacklistService;

	@PostMapping("login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		return authService.login(request);
	}

	@PostMapping("register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		return authService.register(request);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String token = extractTokenFromRequest(request);
		if (token != null) {
			tokenBlacklistService.blacklistToken(token);
			System.out.println("Token blacklisted: " + token);
			return ResponseEntity.ok("Logout successful");
		}
		return ResponseEntity.badRequest().body("No token found");
	}

	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
