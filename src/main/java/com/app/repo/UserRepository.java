package com.app.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String> {
  Optional<AppUser> findByUsername(String username);
}

