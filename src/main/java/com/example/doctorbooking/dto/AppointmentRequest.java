package com.example.doctorbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {
    private Integer userId;
    private Integer doctorId;
    private LocalDateTime appointmentDate;
    private String reason;
}
