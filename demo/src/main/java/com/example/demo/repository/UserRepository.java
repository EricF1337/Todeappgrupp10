package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Här kan du lägga till anpassade frågor om det behövs
    User findByUsername(String username);
}