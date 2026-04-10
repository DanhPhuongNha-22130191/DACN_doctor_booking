package com.example.doctorbooking.dto;

import com.example.doctorbooking.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private Integer id;
    private Integer userId;
    private String userName;
    private Integer doctorId;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
    private String reason;
}
