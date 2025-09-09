package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findByAvailableTrue();
  List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}