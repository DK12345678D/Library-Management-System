package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {
  List<Book> findByAvailableTrue();
  List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}