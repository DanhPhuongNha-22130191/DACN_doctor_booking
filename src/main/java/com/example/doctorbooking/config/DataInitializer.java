package com.example.doctorbooking.config;

import com.example.doctorbooking.entity.Department;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.enums.Status;
import com.example.doctorbooking.repository.DepartmentRepository;
import com.example.doctorbooking.repository.DoctorRepository;
import com.example.doctorbooking.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public void run(String... args) {
        if (hospitalRepository.count() == 0) {
            log.info("Starting data seeding...");

            // Create Hospital 1
            Hospital hospital1 = Hospital.builder()
                    .name("Cho Ray Hospital")
                    .address("201B Nguyen Chi Thanh, Ward 12, District 5, HCMC")
                    .phone("02838554137")
                    .email("choray@choray.org.vn")
                    .build();
            hospital1 = hospitalRepository.save(hospital1);

            // Create Departments for Hospital 1
            Department dep1H1 = Department.builder()
                    .name("Cardiology")
                    .hospital(hospital1)
                    .status(Status.active)
                    .build();
            dep1H1 = departmentRepository.save(dep1H1);

            Department dep2H1 = Department.builder()
                    .name("Neurology")
                    .hospital(hospital1)
                    .status(Status.active)
                    .build();
            dep2H1 = departmentRepository.save(dep2H1);

            // Create Doctors for Hospital 1
            doctorRepository.save(Doctor.builder()
                    .name("Dr. Nguyen Van A")
                    .email("nva@gmail.com")
                    .phone("0901234567")
                    .hospital(hospital1)
                    .department(dep1H1)
                    .status(Status.active)
                    .build());

            doctorRepository.save(Doctor.builder()
                    .name("Dr. Tran Thi B")
                    .email("ttb@gmail.com")
                    .phone("0907654321")
                    .hospital(hospital1)
                    .department(dep2H1)
                    .status(Status.active)
                    .build());

            // Create Hospital 2
            Hospital hospital2 = Hospital.builder()
                    .name("Bach Mai Hospital")
                    .address("78 Giai Phong, Phuong Mai, Dong Da, Hanoi")
                    .phone("02438693731")
                    .email("bachmai@bachmai.gov.vn")
                    .build();
            hospital2 = hospitalRepository.save(hospital2);

            // Create Department for Hospital 2
            Department dep1H2 = Department.builder()
                    .name("Pediatrics")
                    .hospital(hospital2)
                    .status(Status.active)
                    .build();
            dep1H2 = departmentRepository.save(dep1H2);

            // Create Doctor for Hospital 2
            doctorRepository.save(Doctor.builder()
                    .name("Dr. Le Van C")
                    .email("lvc@gmail.com")
                    .phone("0912345678")
                    .hospital(hospital2)
                    .department(dep1H2)
                    .status(Status.active)
                    .build());

            log.info("Data seeding completed successfully.");
        } else {
            log.info("Database already contains data, skipping seeding.");
        }
    }
}
