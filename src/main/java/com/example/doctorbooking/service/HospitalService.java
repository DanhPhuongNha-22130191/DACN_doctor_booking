package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;

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
}
