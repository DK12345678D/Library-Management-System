package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.BorrowRecord;

public interface BorrowRepository extends JpaRepository<BorrowRecord, String> {
  List<BorrowRecord> findByUserId(String userId);
}
