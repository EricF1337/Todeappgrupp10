package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Komponenttester av TaskController med MockMvc
 * Testar HTTP-endpoints isolerat från andra lager.
 */
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    /**
     * Komponenttest:
     * Verifierar att en uppgift kan skapas via POST /api/tasks
     * och att rätt data returneras.
     */

    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("This is a test task.");
        task.setDone(false);

        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Task\",\"description\":\"This is a test task.\",\"done\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    /**
     * Komponenttest:
     * Verifierar att alla uppgifter hämtas korrekt via GET /api/tasks
     * och att svaret är en JSON-array.
     */

    @Test
    public void testGetAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * Komponenttest:
     * Verifierar att en specifik uppgift kan hämtas via GET /api/tasks/{id}
     * och att rätt innehåll returneras.
     */
    @Test
    public void testGetTaskById() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    /**
     * Komponenttest:
     * Verifierar att en uppgift uppdateras korrekt via PUT /api/tasks/{id}
     * och att uppdaterad data returneras.
     */
    @Test
    public void testUpdateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Updated Task");
        task.setDescription("Updated description");
        task.setDone(true);

        when(taskService.updateTask(any(Long.class), any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Task\",\"description\":\"Updated description\",\"done\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.done").value(true));
    }

    /**
     * Komponenttest:
     * Verifierar att en uppgift tas bort korrekt via DELETE /api/tasks/{id}
     * och att rätt statuskod returneras (204 No Content).
     */
    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
