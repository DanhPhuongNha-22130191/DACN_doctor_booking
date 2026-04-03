package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.DoctorDTO;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Xem danh sách bác sĩ
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctor();
    }

    // Thêm bác sĩ
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }

    // Xem chi tiết bác sĩ
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(doctorService.getDoctorDetail(id));
    }
}