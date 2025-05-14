package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;

/**
 * Enhetstester för TaskService:
 * Dessa tester isolerar affärslogiken från databasen genom att mocka TaskRepository.
 */
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Enhetstest:
     * Verifierar att createTask returnerar ett korrekt Task-objekt efter att det sparats via mockad repository.
     */
    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("This is a test task.");
        task.setDone(false);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);
        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
    }

    /**
     * Enhetstest:
     * Verifierar att getAllTasks returnerar en lista med alla tasks som finns i mockad repository.
     */
    @Test
    public void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        tasks.add(task1);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        tasks.add(task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> foundTasks = taskService.getAllTasks();
        assertEquals(2, foundTasks.size());
    }

    /**
     * Enhetstest:
     * Verifierar att en task kan hämtas korrekt via ID från mockad repository.
     */
    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);
        assertNotNull(foundTask);
        assertEquals("Test Task", foundTask.getTitle());
    }

    /**
     * Enhetstest:
     * Verifierar att uppdatering av en befintlig task fungerar korrekt
     * genom att kontrollera att sparad data matchar den uppdaterade inputen.
     */
    @Test
    public void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Old Task");
        existingTask.setDescription("Old Description");
        existingTask.setDone(false);

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated Description");
        updatedTask.setDone(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task taskToSave = invocation.getArgument(0);
            taskToSave.setId(existingTask.getId());
            return taskToSave;
        });

        Task result = taskService.updateTask(1L, updatedTask);
        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(true, result.isDone());
    }

    /**
     * Enhetstest:
     * Verifierar att deleteTask anropar deleteById korrekt i repository.
     */
    @Test
    public void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
