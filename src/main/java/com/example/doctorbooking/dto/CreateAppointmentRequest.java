package com.example.doctorbooking.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateAppointmentRequest {
    private Integer doctorId;
    private Long timeSlotId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String symptoms;
    private String bookingType; // offline, online
}