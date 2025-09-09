package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.model.AppUser;
import com.app.model.Role;
import com.app.repo.BookRepository;
import com.app.repo.UserRepository;

@Component
public class DataInitializer {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@EventListener
	public void onApplicationReady(ApplicationReadyEvent ev) {
		if (userRepository.count() == 0) {
			AppUser admin = new AppUser("admin", passwordEncoder.encode("adminpass"), Role.ROLE_ADMIN);
			AppUser member = new AppUser("member", passwordEncoder.encode("memberpass"), Role.ROLE_MEMBER);
			userRepository.save(admin);
			userRepository.save(member);
		}

		if (bookRepository.count() == 0) {
			bookRepository.save(new com.app.model.Book("Clean Code", "Robert C. Martin", "9780132350884"));
			bookRepository.save(new com.app.model.Book("Effective Java", "Joshua Bloch", "9780134685991"));
			bookRepository.save(new com.app.model.Book("Design Patterns", "Erich Gamma", "9780201633610"));
		}
	}
}