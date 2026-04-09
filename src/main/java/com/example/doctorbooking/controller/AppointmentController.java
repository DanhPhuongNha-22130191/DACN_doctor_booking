package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.CreateAppointmentRequest;
import com.example.doctorbooking.dto.AppointmentResponse;
import com.example.doctorbooking.dto.TimeSlotResponse;
import com.example.doctorbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final BookingService bookingService;

    /**
     * API 1: Lấy danh sách khung giờ khả dụng của bác sĩ
     * GET /api/bookings/slots?doctorId=1&date=2024-12-10
     */
    @GetMapping("/slots")
    public ResponseEntity<List<TimeSlotResponse>> getAvailableSlots(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<TimeSlotResponse> slots = bookingService.getAvailableTimeSlots(doctorId, date);
        return ResponseEntity.ok(slots);
    }

    /**
     * API 2: Tạo đặt lịch khám
     * POST /api/bookings
     */
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @RequestBody CreateAppointmentRequest request) {

        // TODO: Lấy userId từ token (tạm thời fix cứng)
        Long userId = 1L;

        AppointmentResponse response = bookingService.createAppointment(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * API 3: Lấy lịch sử đặt lịch của user
     * GET /api/bookings/my-bookings
     */
    @GetMapping("/my-bookings")
    public ResponseEntity<?> getMyBookings() {
        // TODO: Lấy userId từ token
        Long userId = 1L;
        // TODO: Thêm method trong service
        return ResponseEntity.ok("Get my bookings - Coming soon");
    }

    /**
     * API 4: Hủy lịch hẹn
     * PUT /api/bookings/{bookingCode}/cancel
     */
    @PutMapping("/{bookingCode}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable String bookingCode) {
        // TODO: Thêm method trong service
        return ResponseEntity.ok("Cancel appointment - Coming soon");
    }

    /**
     * API 5: Xác nhận lịch hẹn (cho bác sĩ)
     * PUT /api/bookings/{id}/confirm
     */
    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmAppointment(@PathVariable Long id) {
        // TODO: Thêm method trong service
        return ResponseEntity.ok("Confirm appointment - Coming soon");
    }

    /**
     * API 6: Hoàn thành lịch hẹn (cho bác sĩ)
     * PUT /api/bookings/{id}/complete
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeAppointment(@PathVariable Long id) {
        // TODO: Thêm method trong service
        return ResponseEntity.ok("Complete appointment - Coming soon");
    }
}