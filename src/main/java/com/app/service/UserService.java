package com.app.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.model.AppUser;
import com.app.repo.UserRepository;

@Service
public class UserService {
  private final UserRepository repo;
  private final PasswordEncoder encoder;

  public UserService(UserRepository repo, PasswordEncoder encoder){ this.repo = repo; this.encoder = encoder; }

  public AppUser register(String username, String rawPassword, String role) {
    if (repo.findByUsername(username).isPresent()) throw new RuntimeException("username exists");
    AppUser u = new AppUser();
    u.setId(UUID.randomUUID().toString());
    u.setUsername(username);
    u.setPassword(encoder.encode(rawPassword));
    u.setRole(role == null ? "MEMBER" : role.toUpperCase());
    return repo.save(u);
  }

  public AppUser findByUsername(String username){
    return repo.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found"));
  }
}

