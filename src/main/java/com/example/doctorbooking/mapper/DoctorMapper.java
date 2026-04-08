package com.example.doctorbooking.mapper;

import com.example.doctorbooking.dto.*;
import com.example.doctorbooking.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorDTO toDoctorDTO(Doctor doctor) {
        if (doctor == null) return null;
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .status(doctor.getStatus() != null ? doctor.getStatus().name() : null)
                .hospitalId(doctor.getHospital() != null ? doctor.getHospital().getId() : null)
                .hospitalName(doctor.getHospital() != null ? doctor.getHospital().getName() : null)
                .departmentId(doctor.getDepartment() != null ? doctor.getDepartment().getId() : null)
                .departmentName(doctor.getDepartment() != null ? doctor.getDepartment().getName() : null)
                .build();
    }

    public DoctorResponse toDoctorResponse(Doctor doctor) {
        if (doctor == null) return null;
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .status(doctor.getStatus() != null ? doctor.getStatus().name() : null)
                .hospital(doctor.getHospital() != null ? SimpleHospital.builder()
                        .id(doctor.getHospital().getId())
                        .name(doctor.getHospital().getName())
                        .build() : null)
                .department(doctor.getDepartment() != null ? SimpleDepartment.builder()
                        .id(doctor.getDepartment().getId())
                        .name(doctor.getDepartment().getName())
                        .build() : null)
                .build();
    }
}
