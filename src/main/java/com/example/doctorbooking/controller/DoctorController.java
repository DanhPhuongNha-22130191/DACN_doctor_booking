package com.example.doctorbooking.controller;

import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.service.DoctorService;
import com.example.doctorbooking.service.HospitalService;
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
    // Xoa bac si
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        doctorService.deleteDoctor(id);
        return "Doctor deleted (soft delete)";
    }

}
