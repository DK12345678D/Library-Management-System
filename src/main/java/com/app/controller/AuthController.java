package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AuthRequest;
import com.app.dto.AuthResponse;
import com.app.model.AppUser;
import com.app.model.Role;
import com.app.security.JwtUtil;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * Register a user. Role may be passed as query param ?role=ROLE_MEMBER or
	 * ROLE_ADMIN (optional). In production, creating an ADMIN should be restricted.
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestParam(value = "role", required = false) String roleStr,
			@RequestBody AuthRequest request) {
		Role role = Role.ROLE_MEMBER;
		if (roleStr != null && roleStr.equals("ROLE_ADMIN"))
			role = Role.ROLE_ADMIN;
		try {
			AppUser u = userService.registerUser(request.getUsername(), request.getPassword(), role);
			return ResponseEntity.ok("User registered with id: " + u.getId());
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			String token = jwtUtil.generateToken(request.getUsername());
			return ResponseEntity.ok(new AuthResponse(token));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}
}
