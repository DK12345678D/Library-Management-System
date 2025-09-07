package com.app.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "appusers")
public class AppUser {
  @Id
  private String id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String role; // ADMIN or MEMBER

  @Column(name = "created_at")
  private Instant createdAt = Instant.now();

  // getters & setters
  // constructors
  public AppUser() {}
  // ... standard getters/setters
}


