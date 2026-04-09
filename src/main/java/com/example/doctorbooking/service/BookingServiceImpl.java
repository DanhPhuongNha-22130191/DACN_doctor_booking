package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.CreateAppointmentRequest;
import com.example.doctorbooking.dto.AppointmentResponse;
import com.example.doctorbooking.dto.TimeSlotResponse;
import com.example.doctorbooking.entity.*;
import com.example.doctorbooking.entity.AppointmentStatus;
import com.example.doctorbooking.enums.SlotStatus;
import com.example.doctorbooking.repository.*;
import com.example.doctorbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<TimeSlotResponse> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        List<TimeSlot> slots = timeSlotRepository.findAvailableSlotsByDoctorAndDate(
                Math.toIntExact(doctorId), date);

        return slots.stream()
                .map(slot -> TimeSlotResponse.builder()
                        .id(slot.getId())
                        .slotDate(slot.getSlotDate())
                        .startTime(slot.getStartTime())
                        .endTime(slot.getEndTime())
                        .status(slot.getStatus())
                        .isAvailable("available".equals(slot.getStatus()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request, Long userId) {
        // 1. Kiểm tra user
        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 2. Kiểm tra doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + request.getDoctorId()));

        // 3. Kiểm tra và lock slot
        TimeSlot timeSlot = timeSlotRepository.findAvailableSlotById(request.getTimeSlotId());
        if (timeSlot == null) {
            throw new RuntimeException("Time slot is not available with id: " + request.getTimeSlotId());
        }

        // 4. Kiểm tra trùng lịch
        boolean isDuplicate = appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
                request.getDoctorId(), request.getAppointmentDate(), request.getAppointmentTime(), "cancelled");
        if (isDuplicate) {
            throw new RuntimeException("This time slot is already booked");
        }

        // 5. Tạo booking code
        String bookingCode = generateBookingCode();

        // 6. Tạo appointment
        Appointment appointment = Appointment.builder()
                .bookingCode(bookingCode)
                .user(user)
                .doctor(doctor)
                .timeSlot(timeSlot)
                .appointmentDate(request.getAppointmentDate())
                .appointmentTime(request.getAppointmentTime())
                .symptoms(request.getSymptoms())
                .status(AppointmentStatus.PENDING.name())
                .createdAt(LocalDateTime.now())
                .build();

        // 7. Cập nhật trạng thái time slot
        timeSlot.setStatus(SlotStatus.BOOKED.name());
        timeSlotRepository.save(timeSlot);

        // 8. Lưu appointment
        Appointment saved = appointmentRepository.save(appointment);

        // 9. Trả về response
        return AppointmentResponse.builder()
                .id(saved.getId())
                .bookingCode(saved.getBookingCode())
                .doctorId(doctor.getId())
                .doctorName(doctor.getName())
                .hospitalName(doctor.getHospital() != null ? doctor.getHospital().getName() : null)
                .departmentName(doctor.getDepartment() != null ? doctor.getDepartment().getName() : null)
                .appointmentDate(saved.getAppointmentDate())
                .appointmentTime(saved.getAppointmentTime())
                .symptoms(saved.getSymptoms())
                .status(saved.getStatus())
                .bookingType(request.getBookingType())
                .patientName(user.getFullName())
                .patientPhone(user.getPhone())
                .patientEmail(user.getEmail())
                .build();
    }

    private String generateBookingCode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "BK" + timestamp + random;
    }
}