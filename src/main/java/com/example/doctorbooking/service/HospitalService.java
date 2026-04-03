package com.example.doctorbooking.service;

import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.enums.Status;
import com.example.doctorbooking.repository.DoctorRepository;
import com.example.doctorbooking.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.dto.DepartmentDTO;
import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.repository.DoctorRepository;
import com.example.doctorbooking.repository.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    // Thêm bệnh viện
    public Hospital createHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    // Danh sách bệnh viện
    public List<Hospital> getAllHospital() {
        return hospitalRepository.findByStatus(Status.active);
    }
    // Xoa mot benh vien
    public void deleteHospital(Integer id) {
        Hospital hospital = hospitalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
        hospital.setStatus(Status.inactive);
        hospitalRepository.save(hospital);
        return hospitalRepository.findAll();
    }

    // Chi tiết bệnh viện
    @Transactional(readOnly = true)
    public HospitalDTO getHospitalDetail(Integer id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));

        return HospitalDTO.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .address(hospital.getAddress())
                .phone(hospital.getPhone())
                .email(hospital.getEmail())
                .build();
    }

    public List<HospitalDTO> searchHospitals(String keyword) {
        List<Hospital> hospitals;
        if (keyword == null || keyword.isEmpty()) {
            hospitals = hospitalRepository.findAll();
        } else {
            hospitals = hospitalRepository.searchByKeyword(keyword);
        }

        // Chuyển entity → DTO để tránh vòng lặp JSON
        return hospitals.stream().map(h -> {
            List<DepartmentDTO> deptDTOs = h.getDepartments()
                    .stream()
                    .map(d -> new DepartmentDTO(
                            d.getId(),
                            d.getName(),
                            d.getStatus() != null ? d.getStatus().name() : null
                    ))
                    .collect(Collectors.toList());

            return new HospitalDTO(h.getId(), h.getName(), h.getAddress(), h.getPhone(), h.getEmail(), deptDTOs);
        }).collect(Collectors.toList());
    }
}