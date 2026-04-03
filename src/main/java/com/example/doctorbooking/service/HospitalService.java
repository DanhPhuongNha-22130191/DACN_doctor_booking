package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.dto.DepartmentDTO;
import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.dto.HospitalResponse;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.repository.DoctorRepository;
import com.example.doctorbooking.repository.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import com.example.doctorbooking.enums.Status;

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

    public HospitalResponse mapToResponse(Hospital h) {
        return HospitalResponse.builder()
                .id(h.getId())
                .name(h.getName())
                .address(h.getAddress())
                .phone(h.getPhone())
                .email(h.getEmail())
                .created_at(h.getCreatedAt().toString())
                .updated_at(h.getUpdatedAt().toString())
                .build();
    }

    // Danh sách bệnh viện
    public List<HospitalResponse> getAllHospital() {
        return hospitalRepository.findAll()
                .stream()
                .filter(h -> h.getStatus() == Status.active) // optional (rất nên)
                .map(this::mapToResponse)
                .toList();
    }

    // Xóa mềm bệnh viện
    public void deleteHospital(Integer id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));

        hospital.setStatus(Status.inactive);
        hospitalRepository.save(hospital);
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
    // Update bệnh viện cho admin
    public HospitalDTO updateHospital(Integer id, Hospital hospitalRequest) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));

        hospital.setName(hospitalRequest.getName());
        hospital.setAddress(hospitalRequest.getAddress());
        hospital.setPhone(hospitalRequest.getPhone());
        hospital.setEmail(hospitalRequest.getEmail());

        Hospital updatedHospital = hospitalRepository.save(hospital);
        return mapToDTO(updatedHospital);
    }
    private HospitalDTO mapToDTO(Hospital h) {
        List<DepartmentDTO> deptDTOs = h.getDepartments() == null
                ? List.of()
                : h.getDepartments().stream()
                .map(d -> new DepartmentDTO(
                        d.getId(),
                        d.getName(),
                        d.getStatus() != null ? d.getStatus().name() : null
                ))
                .collect(Collectors.toList());

        return new HospitalDTO(
                h.getId(),
                h.getName(),
                h.getAddress(),
                h.getPhone(),
                h.getEmail(),
                deptDTOs
        );
    }

}