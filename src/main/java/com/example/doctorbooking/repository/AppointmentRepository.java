package com.example.doctorbooking.repository;

import com.example.doctorbooking.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    // Kiểm tra trùng lịch
    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
            Integer doctorId, LocalDate date, LocalTime time, String status);

}
