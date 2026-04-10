package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.AppointmentDTO;
import com.example.doctorbooking.dto.AppointmentRequest;
import com.example.doctorbooking.entity.Appointment;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.User;
import com.example.doctorbooking.exception.AppException;
import com.example.doctorbooking.exception.ErrorCode;
import com.example.doctorbooking.mapper.AppointmentMapper;
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
    private final AppointmentMapper appointmentMapper;

//    @Transactional
//    public AppointmentDTO createAppointment(AppointmentRequest request) {
//        // 1. Kiểm tra ngày hẹn
//        if (request.getAppointmentDate().isBefore(LocalDateTime.now())) {
//            throw new AppException(ErrorCode.INVALID_APPOINTMENT_DATE);
//        }
//
//        // 2. Kiểm tra User
//        User user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//
//        // 3. Kiểm tra Doctor
//        Doctor doctor = doctorRepository.findById(request.getDoctorId())
//                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));
//
//        // 4. Tạo Appointment
//        Appointment appointment = Appointment.builder()
//                .user(user)
//                .doctor(doctor)
//                .appointmentDate(request.getAppointmentDate())
//                .reason(request.getReason())
//                .build();
//
//        Appointment savedAppointment = appointmentRepository.save(appointment);
//
//        // 5. Trả về DTO
//        return appointmentMapper.toAppointmentDTO(savedAppointment);
//    }
}
