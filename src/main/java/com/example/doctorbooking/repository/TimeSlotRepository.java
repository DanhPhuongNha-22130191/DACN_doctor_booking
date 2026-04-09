package com.example.doctorbooking.repository;

import com.example.doctorbooking.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {

    List<TimeSlot> findByDoctorIdAndSlotDateOrderByStartTime(Integer doctorId, LocalDate slotDate);

    @Query("SELECT ts FROM TimeSlot ts WHERE ts.doctor.id = :doctorId AND ts.slotDate = :slotDate AND ts.status = 'available'")
    List<TimeSlot> findAvailableSlotsByDoctorAndDate(@Param("doctorId") Integer doctorId, @Param("slotDate") LocalDate slotDate);

    @Query("SELECT ts FROM TimeSlot ts WHERE ts.id = :slotId AND ts.status = 'available'")
    TimeSlot findAvailableSlotById(@Param("slotId") Long slotId);
}