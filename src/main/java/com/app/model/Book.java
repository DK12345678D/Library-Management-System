package com.app.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String author;

	private String isbn;

	@Column(nullable = false)
	private boolean available = true;

	@Version
	private Integer version;

	@Column(name = "created_at")
	private Instant createdAt = Instant.now();

	// constructors, getters, setters
	public Book() {
	}
	// ...
}
