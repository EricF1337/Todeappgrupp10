package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSavingTask_thenItCanBeFoundByUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        Task task = new Task();
        task.setTitle("Integration test task");
        task.setDescription("Description here");
        task.setDone(false);
        task.setUser(user);

        taskRepository.save(task);

        // Act
        List<Task> tasks = taskRepository.findAll();

        // Assert
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Integration test task");
        assertThat(tasks.get(0).getUser().getUsername()).isEqualTo("testuser");
    }
}
