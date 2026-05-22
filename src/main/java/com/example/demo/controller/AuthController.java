package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user){
        Optional<User> existingUser  = userRepository.findByUsername(user.getUsername());
        if(existingUser.isPresent()){
                throw new RuntimeException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token = jwtService.generateToken(user.getUsername());
        return Map.of("token" , token);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user){
        User found = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));


        if(!passwordEncoder.matches(user.getPassword(),found.getPassword() )){
              throw new RuntimeException("Wrong password");
        }


        String token = jwtService.generateToken(found.getUsername());
        return Map.of("token", token);

    }
    

}
