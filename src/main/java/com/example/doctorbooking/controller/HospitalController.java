package com.example.doctorbooking.controller;

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
    @DeleteMapping("delete/{id}")
    public String deleteHospital(@PathVariable Integer id) {
        hospitalService.deleteHospital(id);
        return "Deleted successfully (soft delete)";
    }

}