package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.*;
import com.example.doctorbooking.entity.Department;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.enums.Status;
import com.example.doctorbooking.exception.AppException;
import com.example.doctorbooking.exception.ErrorCode;
import com.example.doctorbooking.mapper.DoctorMapper;
import com.example.doctorbooking.repository.DepartmentRepository;
import com.example.doctorbooking.repository.DoctorRepository;
import com.example.doctorbooking.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorMapper doctorMapper;

    // Thêm bác sĩ
    @Transactional
    public DoctorDTO createDoctor(DoctorRequest request) {
        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new AppException(ErrorCode.HOSPITAL_NOT_FOUND));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .hospital(hospital)
                .department(department)
                .status(Status.active)
                .build();

        return doctorMapper.toDoctorDTO(doctorRepository.save(doctor));
    }

    @Transactional
    public DoctorDTO updateDoctor(Integer id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));

        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new AppException(ErrorCode.HOSPITAL_NOT_FOUND));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        doctor.setName(request.getName());
        doctor.setPhone(request.getPhone());
        doctor.setEmail(request.getEmail());
        doctor.setHospital(hospital);
        doctor.setDepartment(department);

        return doctorMapper.toDoctorDTO(doctorRepository.save(doctor));
    }

    // Danh sách bác sĩ
    public List<DoctorResponse> getAllDoctor() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDoctorResponse)
                .toList();
    }

    // Xóa mềm bác sĩ
    public void deleteDoctor(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));

        doctor.setStatus(Status.inactive);
        doctorRepository.save(doctor);
    }

    // Chi tiết bác sĩ
    @Transactional(readOnly = true)
    public DoctorDTO getDoctorDetail(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));

        return doctorMapper.toDoctorDTO(doctor);
    }

    public List<DoctorResponse> searchDoctors(String keyword) {
        List<Doctor> doctors;

        if (keyword == null || keyword.trim().isEmpty()) {
            doctors = doctorRepository.findAll();
        } else {
            doctors = doctorRepository.findByNameContainingIgnoreCase(keyword);
        }

        return doctors.stream()
                .map(doctorMapper::toDoctorResponse)
                .toList();
    }
    public List<DoctorDTO> getDoctorsByHospital(Integer hospitalId) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Bệnh viện không tồn tại"));

        List<Doctor> doctors = doctorRepository.findByHospital(hospital);

        return doctors.stream().map(doctor -> DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .status(doctor.getStatus().name())
                .hospitalId(hospital.getId())
                .hospitalName(hospital.getName())
                .departmentId(doctor.getDepartment().getId())
                .departmentName(doctor.getDepartment().getName())
                .build()
        ).collect(Collectors.toList());
    }
}