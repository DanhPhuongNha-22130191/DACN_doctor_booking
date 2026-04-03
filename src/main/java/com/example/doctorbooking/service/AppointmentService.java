package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.AppointmentDTO;
import com.example.doctorbooking.dto.AppointmentRequest;
import com.example.doctorbooking.entity.Appointment;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.User;
import com.example.doctorbooking.repository.AppointmentRepository;
import com.example.doctorbooking.repository.DoctorRepository;
import com.example.doctorbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    @Transactional
    public AppointmentDTO createAppointment(AppointmentRequest request) {
        // 1. Kiểm tra ngày hẹn
        if (request.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Appointment date must be in the future");
        }

        // 2. Kiểm tra User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        // 3. Kiểm tra Doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + request.getDoctorId()));

        // 4. Tạo Appointment
        Appointment appointment = Appointment.builder()
                .user(user)
                .doctor(doctor)
                .appointmentDate(request.getAppointmentDate())
                .reason(request.getReason())
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // 5. Trả về DTO
        return mapToDTO(savedAppointment);
    }

    private AppointmentDTO mapToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .userId(appointment.getUser().getId())
                .userName(appointment.getUser().getFullName())
                .doctorId(appointment.getDoctor().getId())
                .doctorName(appointment.getDoctor().getName())
                .appointmentDate(appointment.getAppointmentDate())
                .status(appointment.getStatus())
                .reason(appointment.getReason())
                .build();
    }
}
