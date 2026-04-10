package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.AppointmentDTO;
import com.example.doctorbooking.dto.AppointmentRequest;
import com.example.doctorbooking.entity.Appointment;
import com.example.doctorbooking.entity.AppointmentStatus;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.User;
import com.example.doctorbooking.exception.AppException;
import com.example.doctorbooking.mapper.AppointmentMapper;
import com.example.doctorbooking.repository.AppointmentRepository;
import com.example.doctorbooking.repository.DoctorRepository;
import com.example.doctorbooking.repository.UserRepository;
import com.example.doctorbooking.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentService appointmentService;

    private User user;
    private Doctor doctor;
    private AppointmentRequest request;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1).fullName("John Doe").build();
        doctor = Doctor.builder().id(1).name("Dr. Smith").build();
        request = AppointmentRequest.builder()
                .userId(1)
                .doctorId(1)
                .appointmentDate(LocalDateTime.now().plusDays(1))
                .reason("Checking")
                .build();
    }

    @Test
    void createAppointment_Success() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        
        Appointment savedAppointment = Appointment.builder()
                .id(1)
                .user(user)
                .doctor(doctor)
                .appointmentDate(request.getAppointmentDate())
                .status(AppointmentStatus.PENDING)
                .reason(request.getReason())
                .build();
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(savedAppointment);

        AppointmentDTO expectedDto = AppointmentDTO.builder()
                .id(1)
                .userName("John Doe")
                .doctorName("Dr. Smith")
                .status(AppointmentStatus.PENDING)
                .build();
        when(appointmentMapper.toAppointmentDTO(any(Appointment.class))).thenReturn(expectedDto);

        // Act
        AppointmentDTO result = appointmentService.createAppointment(request);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getUserName());
        assertEquals("Dr. Smith", result.getDoctorName());
        assertEquals(AppointmentStatus.PENDING, result.getStatus());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void createAppointment_Fail_InvalidDate() {
        // Arrange
        request.setAppointmentDate(LocalDateTime.now().minusDays(1));

        // Act & Assert
        assertThrows(AppException.class, () -> appointmentService.createAppointment(request));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void createAppointment_Fail_UserNotFound() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> appointmentService.createAppointment(request));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void createAppointment_Fail_DoctorNotFound() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(doctorRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> appointmentService.createAppointment(request));
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}
