package com.example.doctorbooking.dto;

import com.example.doctorbooking.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RegisterResponse {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private Role role;
}
