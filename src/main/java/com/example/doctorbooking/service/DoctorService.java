package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.DoctorDTO;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    @Transactional(readOnly = true)
    public DoctorDTO getDoctorDetail(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .status(doctor.getStatus().name())
                .hospitalId(doctor.getHospital() != null ? doctor.getHospital().getId() : null)
                .hospitalName(doctor.getHospital() != null ? doctor.getHospital().getName() : null)
                .departmentId(doctor.getDepartment() != null ? doctor.getDepartment().getId() : null)
                .departmentName(doctor.getDepartment() != null ? doctor.getDepartment().getName() : null)
                .build();
    }
}
