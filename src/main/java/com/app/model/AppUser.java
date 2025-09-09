package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "appusers")
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(unique = true, nullable = false)
	private String username;


	@Column(nullable = false)
	private String password; // stored encoded


	@Enumerated(EnumType.STRING)
	private Role role;

	public AppUser() {}


	public AppUser(String username, String password, Role role) {
	this.username = username;
	this.password = password;
	this.role = role;
	}
	
}
