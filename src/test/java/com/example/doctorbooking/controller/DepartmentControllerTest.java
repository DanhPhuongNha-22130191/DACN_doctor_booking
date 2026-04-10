package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.DepartmentDTO;
import com.example.doctorbooking.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDepartments() throws Exception {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("Cardiology");

        when(departmentService.getAllDepartments()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cardiology"));
    }

    @Test
    void testGetDepartmentsByHospital() throws Exception {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName("Cardiology");

        when(departmentService.getDepartmentsByHospitalId(1)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/departments/hospital/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cardiology"));
    }
}
