package com.example.doctorbooking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String status;
    private Integer hospitalId;
    private Integer departmentId;
    private String hospitalName;
    private String departmentName;
}
