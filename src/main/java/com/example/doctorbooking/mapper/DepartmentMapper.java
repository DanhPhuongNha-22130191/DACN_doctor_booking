package com.example.doctorbooking.mapper;

import com.example.doctorbooking.dto.DepartmentDTO;
import com.example.doctorbooking.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentDTO toDepartmentDTO(Department department) {
        if (department == null) return null;
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .status(department.getStatus() != null ? department.getStatus().name() : null)
                .build();
    }
}
