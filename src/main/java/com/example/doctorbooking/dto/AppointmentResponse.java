package com.example.doctorbooking.dto;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class AppointmentResponse {
    private Long id;
    private String bookingCode;
    private Integer doctorId;
    private String doctorName;
    private String hospitalName;
    private String departmentName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String symptoms;
    private String status;
    private String bookingType;
    private String patientName;
    private String patientPhone;
    private String patientEmail;
}
