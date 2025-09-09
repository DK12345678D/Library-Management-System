package com.app.model;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {


	public Book( String title, String author, String isbn) {
		super();
		
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(nullable = false)
	private String title;


	@Column(nullable = false)
	private String author;


	@Column(unique = true)
	private String isbn;


	@Column(nullable = false)
	private boolean available = true;


	private Long borrowedByUserId;
}
