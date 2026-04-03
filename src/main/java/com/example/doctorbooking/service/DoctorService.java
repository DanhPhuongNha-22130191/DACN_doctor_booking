package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.*;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.example.doctorbooking.enums.Status;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Thêm bác sĩ
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public DoctorResponse mapToResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .status(doctor.getStatus().name())

                .hospital(SimpleHospital.builder()
                        .id(doctor.getHospital().getId())
                        .name(doctor.getHospital().getName())
                        .build())

                .department(SimpleDepartment.builder()
                        .id(doctor.getDepartment().getId())
                        .name(doctor.getDepartment().getName())
                        .build())
                .build();
    }

    // Danh sách bác sĩ
    public List<DoctorResponse> getAllDoctor() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Xóa mềm bác sĩ
    public void deleteDoctor(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        doctor.setStatus(Status.inactive);
        doctorRepository.save(doctor);
    }

    // Chi tiết bác sĩ
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

    public List<DoctorDTO> searchDoctors(String keyword) {
        List<Doctor> doctors;

        if (keyword == null || keyword.trim().isEmpty()) {
            doctors = doctorRepository.findAll();
        } else {
            doctors = doctorRepository.findByNameContainingIgnoreCase(keyword);
        }

        return doctors.stream().map(d -> DoctorDTO.builder()
                .id(d.getId())
                .name(d.getName())
                .phone(d.getPhone())
                .email(d.getEmail())
                .status(d.getStatus() != null ? d.getStatus().name() : null)

                .hospitalId(d.getHospital().getId())
                .hospitalName(d.getHospital().getName())

                .departmentId(d.getDepartment().getId())
                .departmentName(d.getDepartment().getName())
                .build()
        ).toList();
    }
}