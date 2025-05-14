package com.example.demo.repository;

import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integrationstest:
 * Verifierar att en User kan sparas och hämtas korrekt från databasen
 * med hjälp av UserRepository och en in-memory H2-databas.
 */
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Integrationstest:
     * Testar att en användare kan sparas till databasen och hämtas via findById().
     */
    @Test
    void whenSavingUser_thenItCanBeFoundById() {
        // Arrange: skapa och spara en användare
        User user = new User();
        user.setUsername("integrationuser");
        user.setEmail("integration@example.com");

        User savedUser = userRepository.save(user);

        // Act: hämta användaren med ID
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Assert: kontrollera att användaren finns och innehåller korrekt data
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("integrationuser");
        assertThat(foundUser.get().getEmail()).isEqualTo("integration@example.com");
    }
}
