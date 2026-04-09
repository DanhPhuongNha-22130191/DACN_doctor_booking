package com.example.doctorbooking.controller;

import com.example.doctorbooking.entity.User;
import com.example.doctorbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Object login(@RequestBody User request) {
        try {
            return userService.login(request.getUsername(), request.getPassword());
        } catch (Exception e) {
            return java.util.Map.of("error", e.getMessage());
        }
    }
}