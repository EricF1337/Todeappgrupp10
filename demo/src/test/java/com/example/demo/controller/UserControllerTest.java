package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Komponenttester av UserController med MockMvc
 * Syftar till att testa HTTP-endpoints utan att involvera riktig service- eller databaslogik.
 */

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; // Use @MockBean to inject a mock of UserService

    /**
     * Komponenttest:
     * Verifierar att en användare kan skapas via POST /api/users
     * och att korrekt information returneras i JSON-svaret.
     */

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"password\":\"password\",\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    /**
     * Komponenttest:
     * Verifierar att alla användare hämtas korrekt via GET /api/users
     * och att svaret är en JSON-array.
     */
    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(java.util.Collections.emptyList()); // Example mock

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * Komponenttest:
     * Verifierar att en specifik användare kan hämtas via GET /api/users/{id}
     * och att rätt data returneras.
     */
    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }
    /**
     * Komponenttest:
     * Verifierar att en användare uppdateras korrekt via PUT /api/users/{id}
     * och att uppdaterad information returneras.
     */
    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("updateduser");
        user.setEmail("new@example.com");

        when(userService.updateUser(any(Long.class), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"updateduser\",\"password\":\"newpassword\",\"email\":\"new@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.email").value("new@example.com"));
    }
    /**
     * Komponenttest:
     * Verifierar att en användare tas bort korrekt via DELETE /api/users/{id}
     * och att rätt HTTP-status (204 No Content) returneras.
     */

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}