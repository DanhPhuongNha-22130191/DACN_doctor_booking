package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.DoctorDTO;
import com.example.doctorbooking.dto.DoctorRequest;
import com.example.doctorbooking.dto.DoctorResponse;
import com.example.doctorbooking.entity.Department;
import com.example.doctorbooking.entity.Doctor;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.enums.Status;
import com.example.doctorbooking.exception.AppException;
import com.example.doctorbooking.exception.ErrorCode;
import com.example.doctorbooking.mapper.DoctorMapper;
import com.example.doctorbooking.repository.DepartmentRepository;
import com.example.doctorbooking.repository.DoctorRepository;
import com.example.doctorbooking.repository.HospitalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void testCreateDoctor_Success() {
        DoctorRequest request = new DoctorRequest();
        request.setHospitalId(1);
        request.setDepartmentId(1);
        request.setName("Doc Test");

        Hospital hospital = new Hospital();
        Department department = new Department();
        Doctor doctor = Doctor.builder().name("Doc Test").build();
        DoctorDTO dto = new DoctorDTO();

        when(hospitalRepository.findById(1)).thenReturn(Optional.of(hospital));
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        when(doctorMapper.toDoctorDTO(any(Doctor.class))).thenReturn(dto);

        DoctorDTO result = doctorService.createDoctor(request);

        assertNotNull(result);
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void testCreateDoctor_HospitalNotFound() {
        DoctorRequest request = new DoctorRequest();
        request.setHospitalId(1);
        
        when(hospitalRepository.findById(1)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> doctorService.createDoctor(request));
        assertEquals(ErrorCode.HOSPITAL_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testUpdateDoctor_Success() {
        DoctorRequest request = new DoctorRequest();
        request.setHospitalId(1);
        request.setDepartmentId(1);
        
        Doctor doctor = new Doctor();
        Hospital hospital = new Hospital();
        Department department = new Department();
        DoctorDTO dto = new DoctorDTO();

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(hospitalRepository.findById(1)).thenReturn(Optional.of(hospital));
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        when(doctorMapper.toDoctorDTO(any(Doctor.class))).thenReturn(dto);

        DoctorDTO result = doctorService.updateDoctor(1, request);

        assertNotNull(result);
    }

    @Test
    void testGetAllDoctor_Success() {
        Doctor doctor = new Doctor();
        DoctorResponse response = new DoctorResponse();

        when(doctorRepository.findAll()).thenReturn(List.of(doctor));
        when(doctorMapper.toDoctorResponse(any(Doctor.class))).thenReturn(response);

        List<DoctorResponse> result = doctorService.getAllDoctor();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteDoctor_Success() {
        Doctor doctor = Doctor.builder().status(Status.active).build();

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor(1);

        assertEquals(Status.inactive, doctor.getStatus());
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void testSearchDoctors_WithKeyword() {
        Doctor doctor = new Doctor();
        DoctorResponse response = new DoctorResponse();

        when(doctorRepository.findByNameContainingIgnoreCase("Test")).thenReturn(List.of(doctor));
        when(doctorMapper.toDoctorResponse(any(Doctor.class))).thenReturn(response);

        List<DoctorResponse> result = doctorService.searchDoctors("Test");

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
