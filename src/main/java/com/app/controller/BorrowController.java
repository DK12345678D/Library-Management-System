package com.app.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.BorrowRecord;
import com.app.service.BorrowService;

@RestController
@RequestMapping("/api")
public class BorrowController {
  private final BorrowService svc;
  public BorrowController(BorrowService svc){ this.svc = svc; }

  @PostMapping("/books/{id}/borrow")
  public ResponseEntity<?> borrow(@PathVariable String id, Authentication auth){
    String username = auth.getName();
    BorrowRecord r = svc.borrow(id, username);
    return ResponseEntity.ok(r);
  }

  @PostMapping("/books/{id}/return")
  public ResponseEntity<?> returnBook(@PathVariable String id, Authentication auth){
    String username = auth.getName();
    BorrowRecord r = svc.returnBook(id, username);
    return ResponseEntity.ok(r);
  }
}

