package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AuthRequest;
import com.app.dto.AuthResponse;
import com.app.model.AppUser;
import com.app.security.JwtUtil;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final UserService userService;
  private final AuthenticationManager authManager;
  private final JwtUtil jwtUtil;

  public AuthController(UserService userService, AuthenticationManager authManager, JwtUtil jwtUtil){
    this.userService = userService; this.authManager = authManager; this.jwtUtil = jwtUtil;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody AuthRequest req){
    AppUser u = userService.register(req.username, req.password, req.role);
    return ResponseEntity.ok(u);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest req){
    // authenticate credentials
    try {
      authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username, req.password));
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(401).body("invalid credentials");
    }
    AppUser u = userService.findByUsername(req.username);
    String token = jwtUtil.generateToken(u.getUsername(), u.getRole());
    return ResponseEntity.ok(new AuthResponse(token));
  }
}

