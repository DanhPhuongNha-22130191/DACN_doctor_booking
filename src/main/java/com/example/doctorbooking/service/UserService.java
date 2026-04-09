package com.example.doctorbooking.service;

import com.example.doctorbooking.entity.User;
import com.example.doctorbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        String hashedInput = hashPassword(password);

        if (!user.getPassword().equals(hashedInput)) {
            throw new RuntimeException("Sai mật khẩu");
        }
        return user;
    }
}