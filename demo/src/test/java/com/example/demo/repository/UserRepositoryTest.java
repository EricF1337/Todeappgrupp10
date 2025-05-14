package com.example.demo.repository;

import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSavingUser_thenItCanBeFoundById() {
        // Arrange
        User user = new User();
        user.setUsername("integrationuser");
        user.setEmail("integration@example.com");

        User savedUser = userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("integrationuser");
        assertThat(foundUser.get().getEmail()).isEqualTo("integration@example.com");
    }
}
