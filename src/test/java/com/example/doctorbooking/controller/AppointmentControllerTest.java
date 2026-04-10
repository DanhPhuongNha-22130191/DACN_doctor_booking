package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.AppointmentResponse;
import com.example.doctorbooking.dto.CreateAppointmentRequest;
import com.example.doctorbooking.dto.TimeSlotResponse;
import com.example.doctorbooking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private AppointmentController appointmentController;

    @Test
    void testGetAllSlots() {
        TimeSlotResponse slot = TimeSlotResponse.builder().build();
        when(bookingService.getTimeSlots(eq(1L), any(LocalDate.class))).thenReturn(List.of(slot));

        ResponseEntity<List<TimeSlotResponse>> response = appointmentController.getAllSlots(1L, LocalDate.now());
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAvailableSlots() {
        TimeSlotResponse slot = TimeSlotResponse.builder().build();
        when(bookingService.getAvailableTimeSlots(eq(1L), any(LocalDate.class))).thenReturn(List.of(slot));

        ResponseEntity<List<TimeSlotResponse>> response = appointmentController.getAvailableSlots(1L, LocalDate.now());
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testCreateAppointment() {
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        
        AppointmentResponse apptResponse = AppointmentResponse.builder().build();
        when(bookingService.createAppointment(any(CreateAppointmentRequest.class), any(Long.class))).thenReturn(apptResponse);

        ResponseEntity<AppointmentResponse> response = appointmentController.createAppointment(request);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
