package com.example.doctorbooking.service;

import com.example.doctorbooking.dto.HospitalDTO;
import com.example.doctorbooking.dto.HospitalRequest;
import com.example.doctorbooking.dto.HospitalResponse;
import com.example.doctorbooking.entity.Hospital;
import com.example.doctorbooking.enums.Status;
import com.example.doctorbooking.exception.AppException;
import com.example.doctorbooking.exception.ErrorCode;
import com.example.doctorbooking.mapper.HospitalMapper;
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
public class HospitalServiceTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private HospitalMapper hospitalMapper;

    @InjectMocks
    private HospitalService hospitalService;

    @Test
    void testCreateHospital_Success() {
        HospitalRequest request = new HospitalRequest();
        request.setName("Test Hospital");

        Hospital hospital = Hospital.builder().name("Test Hospital").status(Status.active).build();
        HospitalDTO dto = new HospitalDTO();

        when(hospitalRepository.save(any(Hospital.class))).thenReturn(hospital);
        when(hospitalMapper.toHospitalDTO(any(Hospital.class))).thenReturn(dto);

        HospitalDTO result = hospitalService.createHospital(request);

        assertNotNull(result);
        verify(hospitalRepository, times(1)).save(any(Hospital.class));
    }

    @Test
    void testGetAllHospital_Success() {
        Hospital hospital = Hospital.builder().status(Status.active).build();
        HospitalResponse response = new HospitalResponse();

        when(hospitalRepository.findAll()).thenReturn(List.of(hospital));
        when(hospitalMapper.toHospitalResponse(any(Hospital.class))).thenReturn(response);

        List<HospitalResponse> result = hospitalService.getAllHospital();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteHospital_Success() {
        Hospital hospital = Hospital.builder().status(Status.active).build();

        when(hospitalRepository.findById(1)).thenReturn(Optional.of(hospital));

        hospitalService.deleteHospital(1);

        assertEquals(Status.inactive, hospital.getStatus());
        verify(hospitalRepository, times(1)).save(hospital);
    }

    @Test
    void testDeleteHospital_NotFound() {
        when(hospitalRepository.findById(1)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> hospitalService.deleteHospital(1));

        assertEquals(ErrorCode.HOSPITAL_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testGetHospitalDetail_Success() {
        Hospital hospital = new Hospital();
        HospitalDTO dto = new HospitalDTO();

        when(hospitalRepository.findById(1)).thenReturn(Optional.of(hospital));
        when(hospitalMapper.toHospitalDTO(any(Hospital.class))).thenReturn(dto);

        HospitalDTO result = hospitalService.getHospitalDetail(1);

        assertNotNull(result);
    }

    @Test
    void testSearchHospitals_WithKeyword() {
        Hospital hospital = new Hospital();
        HospitalDTO dto = new HospitalDTO();

        when(hospitalRepository.searchByKeyword("Test")).thenReturn(List.of(hospital));
        when(hospitalMapper.toHospitalDTO(any(Hospital.class))).thenReturn(dto);

        List<HospitalDTO> result = hospitalService.searchHospitals("Test");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testSearchHospitals_WithoutKeyword() {
        Hospital hospital = new Hospital();
        HospitalDTO dto = new HospitalDTO();

        when(hospitalRepository.findAll()).thenReturn(List.of(hospital));
        when(hospitalMapper.toHospitalDTO(any(Hospital.class))).thenReturn(dto);

        List<HospitalDTO> result = hospitalService.searchHospitals("");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateHospital_Success() {
        HospitalRequest request = new HospitalRequest();
        Hospital hospital = new Hospital();
        HospitalDTO dto = new HospitalDTO();

        when(hospitalRepository.findById(1)).thenReturn(Optional.of(hospital));
        when(hospitalRepository.save(any(Hospital.class))).thenReturn(hospital);
        when(hospitalMapper.toHospitalDTO(any(Hospital.class))).thenReturn(dto);

        HospitalDTO result = hospitalService.updateHospital(1, request);

        assertNotNull(result);
        verify(hospitalRepository, times(1)).save(hospital);
    }
}
