package com.example.doctorbooking.service;

import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.repository.DoctorRepository;
import com.example.doctorbooking.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return hospitalRepository.findAll();
    }
}