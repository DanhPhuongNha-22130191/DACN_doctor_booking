package com.example.doctorbooking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
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

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(doctorService.getDoctorDetail(id));
    }
}
