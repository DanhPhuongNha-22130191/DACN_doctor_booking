package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.DoctorDTO;
import com.example.doctorbooking.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(doctorService.getDoctorDetail(id));
    }
}
