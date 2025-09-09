package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.BookDto;
import com.app.model.AppUser;
import com.app.service.BookService;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> addBook(@RequestBody BookDto dto) {
		BookDto saved = bookService.addBook(dto);
		return ResponseEntity.ok(saved);
	}

	@GetMapping("/available")
	public ResponseEntity<List<BookDto>> getAvailable() {
		return ResponseEntity.ok(bookService.getAllAvailableBooks());
	}

	@GetMapping("/search")
	public ResponseEntity<List<BookDto>> search(@RequestParam("q") String q) {
		return ResponseEntity.ok(bookService.searchBooks(q));
	}

	@PostMapping("/{id}/borrow")
	@PreAuthorize("hasAnyAuthority('ROLE_MEMBER','ROLE_ADMIN')")
	public ResponseEntity<?> borrow(@PathVariable("id") Long id, Authentication authentication) {
		String username = authentication.getName();
		AppUser user = userService.findByUsername(username).orElse(null);
		if (user == null)
			return ResponseEntity.status(401).body("User not found");
		try {
			return ResponseEntity.ok(bookService.borrowBook(id, user.getId()));
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/{id}/return")
	@PreAuthorize("hasAnyAuthority('ROLE_MEMBER','ROLE_ADMIN')")
	public ResponseEntity<?> returnBook(@PathVariable("id") Long id, Authentication authentication) {
		String username = authentication.getName();
		AppUser user = userService.findByUsername(username).orElse(null);
		if (user == null)
			return ResponseEntity.status(401).body("User not found");
		try {
			return ResponseEntity.ok(bookService.returnBook(id, user.getId()));
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
