package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;


    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/me")
    public User getUser() {
        return userService.getUserByUsername(getCurrentUsername());
    }

    @PutMapping("/me")
    public Map<String, Object> updateUser(@RequestBody User newUser) {
        String username = getCurrentUsername();
        User updated = userService.updateUserByUsername(username, newUser);
        String newToken = jwtService.generateToken(updated.getUsername());
        return Map.of("user", updated, "token", newToken);
    }

    @DeleteMapping("/me")
    public void deletUser() {
        userService.deleteUserByUsername(getCurrentUsername());
    }

}
