package com.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Book;
import com.app.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	private final BookService svc;

	public BookController(BookService svc) {
		this.svc = svc;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> create(@RequestBody Book book) {
		return ResponseEntity.status(201).body(svc.create(book));
	}

	@GetMapping
	public ResponseEntity<List<Book>> list(@RequestParam(required = false) String q,
			@RequestParam(required = false) Boolean available) {
		if (q != null && !q.isBlank())
			return ResponseEntity.ok(svc.search(q));
		if (available != null && available)
			return ResponseEntity.ok(svc.listAvailable());
		return ResponseEntity.ok(svc.listAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> get(@PathVariable String id) {
		return ResponseEntity.ok(svc.getById(id));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> update(@PathVariable String id, @RequestBody Book b) {
		return ResponseEntity.ok(svc.update(id, b));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		svc.delete(id);
		return ResponseEntity.noContent().build();
	}
}
