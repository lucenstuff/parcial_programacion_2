package com.example.parcial.parcial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authz) -> authz
						.requestMatchers("/login.html", "/api/auth/**", "/css/**", "/js/**", "/images/**",
								"/register.html").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin((form) -> form
						.loginPage("/login.html")
						.permitAll()
						.defaultSuccessUrl("/index.html", true)
				)
				.logout(LogoutConfigurer::permitAll);
		return http.build();
	}
}
