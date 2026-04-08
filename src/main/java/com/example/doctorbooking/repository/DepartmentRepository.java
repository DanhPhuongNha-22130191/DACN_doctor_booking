package com.example.doctorbooking.repository;

import com.example.doctorbooking.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findByHospitalId(Integer hospitalId);
}
