package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.DepartmentDTO;
import com.example.doctorbooking.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByHospital(@PathVariable Integer hospitalId) {
        return ResponseEntity.ok(departmentService.getDepartmentsByHospitalId(hospitalId));
    }
}
