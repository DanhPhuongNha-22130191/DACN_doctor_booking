package com.example.doctorbooking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRequest {
    private String name;
    private String phone;
    private String email;
    private Integer hospitalId;
    private Integer departmentId;
}
