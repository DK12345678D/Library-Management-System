package com.app.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.model.AppUser;
import com.app.model.Role;
import com.app.repo.UserRepository;


@Service
public class UserService {


@Autowired
private UserRepository userRepository;


@Autowired
private PasswordEncoder passwordEncoder;


public AppUser registerUser(String username, String rawPassword, Role role) throws IllegalStateException {
Optional<AppUser> existing = userRepository.findByUsername(username);
if (existing.isPresent()) throw new IllegalStateException("Username already exists");
AppUser u = new AppUser(username, passwordEncoder.encode(rawPassword), role);
return userRepository.save(u);
}


public Optional<AppUser> findByUsername(String username) {
return userRepository.findByUsername(username);
}
}

