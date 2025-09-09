package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.BookDto;
import com.app.model.Book;
import com.app.repo.BookRepository;
import com.app.repo.UserRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public BookDto addBook(BookDto dto) {
        Book book = new Book(dto.getTitle(), dto.getAuthor(), dto.getIsbn());
        Book saved = bookRepository.save(book);
        return toDto(saved);
    }

    public List<BookDto> getAllAvailableBooks() {
        return bookRepository.findByAvailableTrue()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> searchBooks(String q) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDto borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available");
        }

        book.setAvailable(false);
        book.setBorrowedByUserId(userId);
        Book saved = bookRepository.save(book);

        return toDto(saved);
    }

    @Transactional
    public BookDto returnBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        if (book.isAvailable() || book.getBorrowedByUserId() == null) {
            throw new IllegalStateException("This book is not currently borrowed");
        }

        if (!book.getBorrowedByUserId().equals(userId)) {
            throw new IllegalStateException("This book was borrowed by another user");
        }

        book.setAvailable(true);
        book.setBorrowedByUserId(null);
        Book saved = bookRepository.save(book);

        return toDto(saved);
    }

    private BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.isAvailable()
        );
    }
}
