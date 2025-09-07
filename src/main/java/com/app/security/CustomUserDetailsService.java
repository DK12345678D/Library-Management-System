package com.app.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.model.AppUser;
import com.app.repo.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository repo;
  public CustomUserDetailsService(UserRepository repo){ this.repo = repo; }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser u = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return User.builder()
        .username(u.getUsername())
        .password(u.getPassword())
        .roles(u.getRole())
        .build();
  }
}
