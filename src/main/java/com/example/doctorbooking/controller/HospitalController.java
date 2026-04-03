package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

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

    // Xoá bệnh viện (xoá mềm)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHospital(@PathVariable Integer id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.ok("Hospital deleted successfully");
    }

    // Xem chi tiết bệnh viện
    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> getHospitalDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(hospitalService.getHospitalDetail(id));
    }

    // Search
    @GetMapping("/search")
    public List<HospitalDTO> searchHospitals(@RequestParam(required = false) String keyword) {
        return hospitalService.searchHospitals(keyword);
    }
    // Admin update hospital
    @PutMapping("/admin/{id}")
    public ResponseEntity<HospitalDTO> updateHospital(
            @PathVariable Integer id,
            @RequestBody Hospital hospitalRequest
    ) {
        return ResponseEntity.ok(hospitalService.updateHospital(id, hospitalRequest));
    }

}