package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.dto.HospitalRequest;
import com.example.doctorbooking.dto.HospitalResponse;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.enums.Status;
import com.example.doctorbooking.exception.AppException;
import com.example.doctorbooking.exception.ErrorCode;
import com.example.doctorbooking.mapper.HospitalMapper;
import com.example.doctorbooking.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;

    // Thêm bệnh viện
    public HospitalDTO createHospital(HospitalRequest request) {
        Hospital hospital = Hospital.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .status(Status.active)
                .build();
        return hospitalMapper.toHospitalDTO(hospitalRepository.save(hospital));
    }

    // Danh sách bệnh viện
    public List<HospitalResponse> getAllHospital() {
        return hospitalRepository.findAll()
                .stream()
                .filter(h -> h.getStatus() == Status.active)
                .map(hospitalMapper::toHospitalResponse)
                .toList();
    }

    // Xóa mềm bệnh viện
    public void deleteHospital(Integer id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HOSPITAL_NOT_FOUND));

        hospital.setStatus(Status.inactive);
        hospitalRepository.save(hospital);
    }

    // Chi tiết bệnh viện
    @Transactional(readOnly = true)
    public HospitalDTO getHospitalDetail(Integer id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HOSPITAL_NOT_FOUND));

        return hospitalMapper.toHospitalDTO(hospital);
    }

    public List<HospitalDTO> searchHospitals(String keyword) {
        List<Hospital> hospitals;
        if (keyword == null || keyword.isEmpty()) {
            hospitals = hospitalRepository.findAll();
        } else {
            hospitals = hospitalRepository.searchByKeyword(keyword);
        }

        return hospitals.stream()
                .map(hospitalMapper::toHospitalDTO)
                .toList();
    }

    // Update bệnh viện cho admin
    @Transactional
    public HospitalDTO updateHospital(Integer id, HospitalRequest request) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HOSPITAL_NOT_FOUND));

        hospital.setName(request.getName());
        hospital.setAddress(request.getAddress());
        hospital.setPhone(request.getPhone());
        hospital.setEmail(request.getEmail());

        return hospitalMapper.toHospitalDTO(hospitalRepository.save(hospital));
    }
}

