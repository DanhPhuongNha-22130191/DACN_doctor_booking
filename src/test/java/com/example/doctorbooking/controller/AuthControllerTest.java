package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.RegisterRequest;
import com.example.doctorbooking.dto.RegisterResponse;
import com.example.doctorbooking.entity.User;
import com.example.doctorbooking.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");

        RegisterResponse response = RegisterResponse.builder().username("testuser").build();

        when(userService.register(any(RegisterRequest.class))).thenReturn(response);

        RegisterResponse result = authController.register(request);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testLogin_Success() {
        User request = new User();
        request.setUsername("testuser");
        request.setPassword("password");

        User user = new User();
        user.setUsername("testuser");

        when(userService.login(anyString(), anyString())).thenReturn(user);

        Object result = authController.login(request);
        assertTrue(result instanceof User);
        assertEquals("testuser", ((User) result).getUsername());
    }

    @Test
    void testLogin_Fail() {
        User request = new User();
        request.setUsername("testuser");
        request.setPassword("wrong");

        when(userService.login(anyString(), anyString())).thenThrow(new RuntimeException("Sai mật khẩu"));

        Object result = authController.login(request);
        assertTrue(result instanceof Map);
        assertEquals("Sai mật khẩu", ((Map<?, ?>) result).get("error"));
    }
}
