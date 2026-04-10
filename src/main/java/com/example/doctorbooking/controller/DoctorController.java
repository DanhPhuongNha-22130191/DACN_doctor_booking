package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.DoctorDTO;
import com.example.doctorbooking.dto.DoctorRequest;
import com.example.doctorbooking.dto.DoctorResponse;
import com.example.doctorbooking.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    // Xem danh sách bác sĩ
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctor());
    }

    // Thêm bác sĩ
    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.createDoctor(request));
    }

    // Xoá bác sĩ (xoá mềm)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Integer id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }

    // Sửa thông tin bác sĩ
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Integer id, @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, request));
    }

    // Xem chi tiết bác sĩ
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(doctorService.getDoctorDetail(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DoctorResponse>> searchDoctors(
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(doctorService.searchDoctors(keyword));
    }
    // API lấy ds bác sĩ theo bệnh viện
    @GetMapping("/hospital/{hospitalId}")
    public List<DoctorDTO> getDoctorsByHospital(@PathVariable Integer hospitalId) {
        return doctorService.getDoctorsByHospital(hospitalId);
    }
}