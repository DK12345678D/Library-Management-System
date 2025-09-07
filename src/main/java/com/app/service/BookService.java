package com.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.exception.ResourceNotFoundException;
import com.app.model.Book;
import com.app.repo.BookRepository;

@Service
public class BookService {
  private final BookRepository repo;
  public BookService(BookRepository repo){ this.repo = repo; }

  public Book create(Book b){
    b.setId(UUID.randomUUID().toString());
    return repo.save(b);
  }

  public List<Book> listAll(){ return repo.findAll(); }

  public List<Book> listAvailable(){ return repo.findByAvailableTrue(); }

  public List<Book> search(String q){
    return repo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q);
  }

  public Book getById(String id){
    return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
  }

  @Transactional
  public Book update(String id, Book update){
    Book b = getById(id);
    if (update.getTitle() != null) b.setTitle(update.getTitle());
    if (update.getAuthor() != null) b.setAuthor(update.getAuthor());
    if (update.getIsbn() != null) b.setIsbn(update.getIsbn());
    b.setAvailable(update.isAvailable());
    return repo.save(b);
  }

  public void delete(String id){ repo.deleteById(id); }
}
