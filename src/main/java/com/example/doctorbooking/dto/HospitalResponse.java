package com.example.doctorbooking.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalResponse {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private List<DepartmentDTO> departments;

    private String created_at;
    private String updated_at;
}
