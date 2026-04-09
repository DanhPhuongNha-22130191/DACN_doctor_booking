package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.CreateAppointmentRequest;
import com.example.doctorbooking.dto.AppointmentResponse;
import com.example.doctorbooking.dto.TimeSlotResponse;
import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    List<TimeSlotResponse> getTimeSlots(Long doctorId, LocalDate date);

    // API 1: Lấy danh sách khung giờ khả dụng của bác sĩ
    List<TimeSlotResponse> getAvailableTimeSlots(Long doctorId, LocalDate date);

    // API 2: Tạo đặt lịch khám
    AppointmentResponse createAppointment(CreateAppointmentRequest request, Long userId);
}