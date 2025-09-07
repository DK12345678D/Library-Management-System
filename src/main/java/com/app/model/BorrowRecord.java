package com.app.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "borrow_records")
public class BorrowRecord {
  @Id
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Book book;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private AppUser user;

  @Column(name = "borrowed_at")
  private Instant borrowedAt;

  @Column(name = "returned_at")
  private Instant returnedAt;

  private String status; // BORROWED / RETURNED

  // constructors, getters, setters
  public BorrowRecord(){}
  // ...
}
