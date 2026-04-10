package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.RegisterRequest;
import com.example.doctorbooking.dto.RegisterResponse;
import com.example.doctorbooking.entity.User;
import com.example.doctorbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }
    @PostMapping("/login")
    public Object login(@RequestBody User request) {
        try {
            return userService.login(request.getUsername(), request.getPassword());
        } catch (Exception e) {
            return java.util.Map.of("error", e.getMessage());
        }
    }
}
