package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.DepartmentDTO;
import com.example.doctorbooking.mapper.DepartmentMapper;
import com.example.doctorbooking.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentDTO> getDepartmentsByHospitalId(Integer hospitalId) {
        System.out.println("Fetching departments for hospital ID: " + hospitalId);
        
        List<DepartmentDTO> departments = departmentRepository.findByHospitalId(hospitalId)
                .stream()
                .map(departmentMapper::toDepartmentDTO)
                .toList();
        
        System.out.println("Result count: " + departments.size());
        departments.forEach(d -> System.out.println(" - Department: " + d.getName()));
        
        return departments;
    }

    public List<DepartmentDTO> getAllDepartments() {
        System.out.println("Fetching all departments");
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDepartmentDTO)
                .toList();
    }
}
