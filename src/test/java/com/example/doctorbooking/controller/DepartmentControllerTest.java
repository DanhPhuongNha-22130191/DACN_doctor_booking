package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.DepartmentDTO;
import com.example.doctorbooking.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @Test
    void testGetDepartments() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("Cardiology");

        when(departmentService.getAllDepartments()).thenReturn(List.of(dto));

        ResponseEntity<List<DepartmentDTO>> result = departmentController.getDepartments();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Cardiology", result.getBody().get(0).getName());
    }

    @Test
    void testGetDepartmentsByHospital() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("Cardiology");

        when(departmentService.getDepartmentsByHospitalId(1)).thenReturn(List.of(dto));

        ResponseEntity<List<DepartmentDTO>> result = departmentController.getDepartmentsByHospital(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Cardiology", result.getBody().get(0).getName());
    }
}
