package com.example.doctorbooking.repository;

import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByStatus(Status status);

}
