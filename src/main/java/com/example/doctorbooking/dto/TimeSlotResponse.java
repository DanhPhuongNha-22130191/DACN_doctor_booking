package com.example.doctorbooking.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class TimeSlotResponse {
    private Long id;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status; // available, booked, blocked
    private Boolean isAvailable;
}