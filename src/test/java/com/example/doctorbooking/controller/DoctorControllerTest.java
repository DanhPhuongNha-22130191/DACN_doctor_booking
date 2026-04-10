package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.DoctorDTO;
import com.example.doctorbooking.dto.DoctorRequest;
import com.example.doctorbooking.dto.DoctorResponse;
import com.example.doctorbooking.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters wrapper
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllDoctors() throws Exception {
        DoctorResponse response = new DoctorResponse();
        response.setName("Dr. Test");

        when(doctorService.getAllDoctor()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Dr. Test"));
    }

    @Test
    void testCreateDoctor() throws Exception {
        DoctorRequest request = new DoctorRequest();
        request.setName("Dr. New");
        
        DoctorDTO dto = new DoctorDTO();
        dto.setName("Dr. New");

        when(doctorService.createDoctor(any(DoctorRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. New"));
    }

    @Test
    void testDeleteDoctor() throws Exception {
        mockMvc.perform(delete("/api/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Doctor deleted successfully"));
    }

    @Test
    void testUpdateDoctor() throws Exception {
        DoctorRequest request = new DoctorRequest();
        request.setName("Dr. Updated");

        DoctorDTO dto = new DoctorDTO();
        dto.setName("Dr. Updated");

        when(doctorService.updateDoctor(eq(1), any(DoctorRequest.class))).thenReturn(dto);

        mockMvc.perform(put("/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. Updated"));
    }

    @Test
    void testGetDoctorDetail() throws Exception {
        DoctorDTO dto = new DoctorDTO();
        dto.setName("Dr. Detail");

        when(doctorService.getDoctorDetail(1)).thenReturn(dto);

        mockMvc.perform(get("/api/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. Detail"));
    }
}
