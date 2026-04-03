package com.example.doctorbooking.service;

import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.enums.Status;
import lombok.RequiredArgsConstructor;
import com.example.doctorbooking.dto.DoctorDTO;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.print.Doc;
import java.util.List;

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

    // Danh sách bác sĩ
    public List<Doctor> getAllDoctor() {
        return doctorRepository.findByStatus(Status.active);
    }
    // Xoa mot bac si
    public void deleteDoctor(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctor.setStatus(Status.inactive);
        doctorRepository.save(doctor);
    }

        return doctorRepository.findAll();
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
}