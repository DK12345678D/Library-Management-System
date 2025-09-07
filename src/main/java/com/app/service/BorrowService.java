package com.app.service;


import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.exception.ResourceNotFoundException;
import com.app.model.AppUser;
import com.app.model.Book;
import com.app.model.BorrowRecord;
import com.app.repo.BookRepository;
import com.app.repo.BorrowRepository;
import com.app.repo.UserRepository;

@Service
public class BorrowService {
  private final BorrowRepository borrowRepo;
  private final BookRepository bookRepo;
  private final UserRepository userRepo;

  public BorrowService(BorrowRepository br, BookRepository bRepo, UserRepository uRepo){
    this.borrowRepo = br; this.bookRepo = bRepo; this.userRepo = uRepo;
  }

  @Transactional
  public BorrowRecord borrow(String bookId, String username){
    Book book = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    if (!book.isAvailable()) throw new RuntimeException("Book not available");

    // mark unavailable
    book.setAvailable(false);
    bookRepo.save(book);

    BorrowRecord r = new BorrowRecord();
    r.setId(UUID.randomUUID().toString());
    r.setBook(book);
    AppUser user = userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    r.setUser(user);
    r.setBorrowedAt(Instant.now());
    r.setStatus("BORROWED");
    return borrowRepo.save(r);
  }

  @Transactional
  public BorrowRecord returnBook(String bookId, String username){
    // find the active borrow record for this book and user
    var list = borrowRepo.findAll(); // simple approach; can add query to find by book+user+status
    BorrowRecord rec = list.stream()
        .filter(r -> r.getBook().getId().equals(bookId) && r.getUser().getUsername().equals(username) && "BORROWED".equals(r.getStatus()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Active borrow record not found"));

    rec.setReturnedAt(Instant.now());
    rec.setStatus("RETURNED");
    // mark book available
    Book book = rec.getBook();
    book.setAvailable(true);
    bookRepo.save(book);
    return borrowRepo.save(rec);
  }
}
