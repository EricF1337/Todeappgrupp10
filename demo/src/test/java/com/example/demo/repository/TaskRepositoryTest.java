package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integrationstest:
 * Verifierar att en Task kan sparas i databasen och hämtas korrekt via TaskRepository.
 * Använder @DataJpaTest som laddar en in-memory H2-databas för verklig databasinteraktion.
 */
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSavingTask_thenItCanBeFoundByUser() {
        // Arrange: skapa och spara en användare
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        // Skapa och koppla en Task till användaren
        Task task = new Task();
        task.setTitle("Integration test task");
        task.setDescription("Description here");
        task.setDone(false);
        task.setUser(user);
        taskRepository.save(task);

        // Act: hämta alla tasks
        List<Task> tasks = taskRepository.findAll();

        // Assert: kontrollera att tasken finns och innehåller rätt data
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Integration test task");
        assertThat(tasks.get(0).getUser().getUsername()).isEqualTo("testuser");
    }
}
