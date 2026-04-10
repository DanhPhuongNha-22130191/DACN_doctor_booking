package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.RegisterRequest;
import com.example.doctorbooking.dto.RegisterResponse;
import com.example.doctorbooking.entity.User;
import com.example.doctorbooking.enums.Role;
import com.example.doctorbooking.exception.AppException;
import com.example.doctorbooking.exception.ErrorCode;
import com.example.doctorbooking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegister_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("123456");
        request.setEmail("test@email.com");
        request.setFullName("Test User");

        User user = User.builder()
                .username("testuser")
                .email("test@email.com")
                .role(Role.PATIENT)
                .build();

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@email.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        RegisterResponse response = userService.register(request);

        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
    }

    @Test
    void testRegister_UsernameExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("123456");
        request.setEmail("test@email.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> userService.register(request));
        assertEquals(ErrorCode.USERNAME_ALREADY_EXISTS, exception.getErrorCode());
    }

    @Test
    void testRegister_InvalidEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("123456");
        request.setEmail("invalid-email");

        AppException exception = assertThrows(AppException.class, () -> userService.register(request));
        assertEquals(ErrorCode.EMAIL_INVALID, exception.getErrorCode());
    }

    @Test
    void testLogin_Success() {
        String hashedPassword = userService.hashPassword("password");
        User user = User.builder().username("testuser").password(hashedPassword).build();
        
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User result = userService.login("testuser", "password");
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.login("testuser", "password"));
        assertEquals("Không tìm thấy user", exception.getMessage());
    }

    @Test
    void testLogin_WrongPassword() {
        User user = User.builder().username("testuser").password("wronghash").build();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.login("testuser", "password"));
        assertEquals("Sai mật khẩu", exception.getMessage());
    }
}
