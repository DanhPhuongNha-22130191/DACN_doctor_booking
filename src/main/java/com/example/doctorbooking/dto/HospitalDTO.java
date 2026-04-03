package com.example.doctorbooking.dto;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalDTO {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private List<DepartmentDTO> departments; // chỉ trả department cần thiết
}