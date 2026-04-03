package com.example.doctorbooking.repository;

import com.example.doctorbooking.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
    // Tìm theo keyword ở name hoặc address
    @Query("SELECT h FROM Hospital h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(h.address) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Hospital> searchByKeyword(@Param("keyword") String keyword);
}
