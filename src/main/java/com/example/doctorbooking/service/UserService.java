package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.RegisterRequest;
import com.example.doctorbooking.dto.RegisterResponse;
import com.example.doctorbooking.entity.User;
import com.example.doctorbooking.enums.Role;
import com.example.doctorbooking.exception.AppException;
import com.example.doctorbooking.exception.ErrorCode;
import com.example.doctorbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {
        validateRegisterRequest(request);

        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        if (userRepository.existsByEmail(request.getEmail().trim())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .username(request.getUsername().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail().trim())
                .fullName(normalizeNullable(request.getFullName()))
                .phone(normalizeNullable(request.getPhone()))
                .role(Role.PATIENT)
                .build();

        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .phone(savedUser.getPhone())
                .role(savedUser.getRole())
                .build();
    }

    private void validateRegisterRequest(RegisterRequest request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_REGISTER_REQUEST);
        }

        if (!StringUtils.hasText(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_REQUIRED);
        }

        if (request.getUsername().trim().length() < 3) {
            throw new AppException(ErrorCode.USERNAME_INVALID);
        }

        if (!StringUtils.hasText(request.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_REQUIRED);
        }

        if (request.getPassword().length() < 6) {
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }

        if (!StringUtils.hasText(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_REQUIRED);
        }

        String email = request.getEmail().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new AppException(ErrorCode.EMAIL_INVALID);
        }
    }

    private String normalizeNullable(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }
}
