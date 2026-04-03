package com.example.doctorbooking.controller;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> getHospitalDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(hospitalService.getHospitalDetail(id));
    }
}
