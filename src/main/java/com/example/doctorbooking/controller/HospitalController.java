package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.dto.HospitalRequest;
import com.example.doctorbooking.dto.HospitalResponse;
import com.example.doctorbooking.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    // Xem danh sách bệnh viện
    @GetMapping
    public ResponseEntity<List<HospitalResponse>> getAll() {
        return ResponseEntity.ok(hospitalService.getAllHospital());
    }

    // Thêm bệnh viện
    @PostMapping
    public ResponseEntity<HospitalDTO> createHospital(@RequestBody HospitalRequest request) {
        return ResponseEntity.ok(hospitalService.createHospital(request));
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
    public ResponseEntity<List<HospitalDTO>> searchHospitals(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(hospitalService.searchHospitals(keyword));
    }

    // Admin update hospital
    @PutMapping("/admin/{id}")
    public ResponseEntity<HospitalDTO> updateHospital(
            @PathVariable Integer id,
            @RequestBody HospitalRequest hospitalRequest
    ) {
        return ResponseEntity.ok(hospitalService.updateHospital(id, hospitalRequest));
    }
}