package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.DoctorDTO;
import com.example.doctorbooking.dto.DoctorRequest;
import com.example.doctorbooking.dto.DoctorResponse;
import com.example.doctorbooking.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @Test
    void testGetAllDoctors() {
        DoctorResponse response = new DoctorResponse();
        response.setName("Dr. Test");

        when(doctorService.getAllDoctor()).thenReturn(List.of(response));

        ResponseEntity<List<DoctorResponse>> result = doctorController.getAllDoctors();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Dr. Test", result.getBody().get(0).getName());
    }

    @Test
    void testCreateDoctor() {
        DoctorRequest request = new DoctorRequest();
        request.setName("Dr. New");
        
        DoctorDTO dto = new DoctorDTO();
        dto.setName("Dr. New");

        when(doctorService.createDoctor(any(DoctorRequest.class))).thenReturn(dto);

        ResponseEntity<DoctorDTO> result = doctorController.createDoctor(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Dr. New", result.getBody().getName());
    }

    @Test
    void testDeleteDoctor() {
        ResponseEntity<String> result = doctorController.deleteDoctor(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Doctor deleted successfully", result.getBody());
        verify(doctorService).deleteDoctor(1);
    }

    @Test
    void testUpdateDoctor() {
        DoctorRequest request = new DoctorRequest();
        request.setName("Dr. Updated");

        DoctorDTO dto = new DoctorDTO();
        dto.setName("Dr. Updated");

        when(doctorService.updateDoctor(eq(1), any(DoctorRequest.class))).thenReturn(dto);

        ResponseEntity<DoctorDTO> result = doctorController.updateDoctor(1, request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Dr. Updated", result.getBody().getName());
    }

    @Test
    void testGetDoctorDetail() {
        DoctorDTO dto = new DoctorDTO();
        dto.setName("Dr. Detail");

        when(doctorService.getDoctorDetail(1)).thenReturn(dto);

        ResponseEntity<DoctorDTO> result = doctorController.getDoctorDetail(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Dr. Detail", result.getBody().getName());
    }
}
