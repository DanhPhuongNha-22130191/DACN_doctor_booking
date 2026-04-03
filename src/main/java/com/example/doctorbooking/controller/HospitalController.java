package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    // Xem danh sách bệnh viện
    @GetMapping
    public List<Hospital> getAll() {
        return hospitalService.getAllHospital();
    }

    // Thêm bệnh viện
    @PostMapping
    public Hospital createHospital(@RequestBody Hospital hospital) {
        return hospitalService.createHospital(hospital);
    }

    // Search
    @GetMapping("/search")
    public List<HospitalDTO> searchHospitals(@RequestParam(required = false) String keyword) {
        return hospitalService.searchHospitals(keyword);
    }

}