package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.DepartmentDTO;
import com.example.doctorbooking.entity.Department;
import com.example.doctorbooking.mapper.DepartmentMapper;
import com.example.doctorbooking.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void testGetDepartmentsByHospitalId_Success() {
        Department department = new Department();
        DepartmentDTO dto = new DepartmentDTO();

        when(departmentRepository.findByHospitalId(1)).thenReturn(List.of(department));
        when(departmentMapper.toDepartmentDTO(any(Department.class))).thenReturn(dto);

        List<DepartmentDTO> result = departmentService.getDepartmentsByHospitalId(1);
        
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllDepartments_Success() {
        Department department = new Department();
        DepartmentDTO dto = new DepartmentDTO();

        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(departmentMapper.toDepartmentDTO(any(Department.class))).thenReturn(dto);

        List<DepartmentDTO> result = departmentService.getAllDepartments();
        
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
