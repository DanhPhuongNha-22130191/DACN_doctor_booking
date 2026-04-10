package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.dto.HospitalRequest;
import com.example.doctorbooking.dto.HospitalResponse;
import com.example.doctorbooking.service.HospitalService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HospitalControllerTest {

    @Mock
    private HospitalService hospitalService;

    @InjectMocks
    private HospitalController hospitalController;

    @Test
    void testGetAll() {
        HospitalResponse response = new HospitalResponse();
        response.setName("Test Hospital");

        when(hospitalService.getAllHospital()).thenReturn(List.of(response));

        ResponseEntity<List<HospitalResponse>> result = hospitalController.getAll();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Test Hospital", result.getBody().get(0).getName());
    }

    @Test
    void testCreateHospital() {
        HospitalRequest request = new HospitalRequest();
        request.setName("New Hospital");

        HospitalDTO dto = new HospitalDTO();
        dto.setName("New Hospital");

        when(hospitalService.createHospital(any(HospitalRequest.class))).thenReturn(dto);

        ResponseEntity<HospitalDTO> result = hospitalController.createHospital(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("New Hospital", result.getBody().getName());
    }

    @Test
    void testDeleteHospital() {
        ResponseEntity<String> result = hospitalController.deleteHospital(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Hospital deleted successfully", result.getBody());
        verify(hospitalService).deleteHospital(1);
    }
}
