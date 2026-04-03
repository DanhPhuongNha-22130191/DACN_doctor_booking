package com.example.doctorbooking.service;

import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.Hospital;
import lombok.RequiredArgsConstructor;
import com.example.doctorbooking.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Thêm bác sĩ
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Danh sách bác sĩ
    public List<Doctor> getAllDoctor() {
        return doctorRepository.findAll();
    }

}
