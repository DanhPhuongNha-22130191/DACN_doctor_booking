package com.example.doctorbooking.mapper;

import com.example.doctorbooking.dto.AppointmentDTO;
import com.example.doctorbooking.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    public AppointmentDTO toAppointmentDTO(Appointment appointment) {
        if (appointment == null) return null;
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
