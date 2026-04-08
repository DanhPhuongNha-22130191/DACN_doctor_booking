package com.example.doctorbooking.mapper;

import com.example.doctorbooking.dto.DepartmentDTO;
import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.dto.HospitalResponse;
import com.example.doctorbooking.entity.Hospital;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HospitalMapper {

    public HospitalResponse toHospitalResponse(Hospital hospital) {
        if (hospital == null) return null;
        
        System.out.println("Mapping Hospital to Response: " + hospital.getName());
        if (hospital.getDepartments() != null) {
            System.out.println("Departments count: " + hospital.getDepartments().size());
        } else {
            System.out.println("Departments list is NULL in Hospital entity");
        }

        List<DepartmentDTO> deptDTOs = hospital.getDepartments() == null
                ? List.of()
                : hospital.getDepartments().stream()
                .map(d -> new DepartmentDTO(
                        d.getId(),
                        d.getName(),
                        d.getStatus() != null ? d.getStatus().name() : null
                ))
                .collect(Collectors.toList());

        return HospitalResponse.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .address(hospital.getAddress())
                .phone(hospital.getPhone())
                .email(hospital.getEmail())
                .departments(deptDTOs)
                .created_at(hospital.getCreatedAt() != null ? hospital.getCreatedAt().toString() : null)
                .updated_at(hospital.getUpdatedAt() != null ? hospital.getUpdatedAt().toString() : null)
                .build();
    }

    public HospitalDTO toHospitalDTO(Hospital hospital) {
        if (hospital == null) return null;
        
        System.out.println("Mapping Hospital to DTO: " + hospital.getName());
        if (hospital.getDepartments() != null) {
            System.out.println("Departments count: " + hospital.getDepartments().size());
        } else {
            System.out.println("Departments list is NULL");
        }

        List<DepartmentDTO> deptDTOs = hospital.getDepartments() == null
                ? List.of()
                : hospital.getDepartments().stream()
                .map(d -> new DepartmentDTO(
                        d.getId(),
                        d.getName(),
                        d.getStatus() != null ? d.getStatus().name() : null
                ))
                .collect(Collectors.toList());

        return HospitalDTO.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .address(hospital.getAddress())
                .phone(hospital.getPhone())
                .email(hospital.getEmail())
                .departments(deptDTOs)
                .build();
    }
}
