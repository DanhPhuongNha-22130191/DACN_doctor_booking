package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.dto.HospitalRequest;
import com.example.doctorbooking.dto.HospitalResponse;
import com.example.doctorbooking.service.HospitalService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HospitalController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HospitalService hospitalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        HospitalResponse response = new HospitalResponse();
        response.setName("Test Hospital");

        when(hospitalService.getAllHospital()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/hospitals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Hospital"));
    }

    @Test
    void testCreateHospital() throws Exception {
        HospitalRequest request = new HospitalRequest();
        request.setName("New Hospital");

        HospitalDTO dto = new HospitalDTO();
        dto.setName("New Hospital");

        when(hospitalService.createHospital(any(HospitalRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/api/hospitals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Hospital"));
    }

    @Test
    void testDeleteHospital() throws Exception {
        mockMvc.perform(delete("/api/hospitals/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hospital deleted successfully"));

        verify(hospitalService).deleteHospital(1);
    }
}
