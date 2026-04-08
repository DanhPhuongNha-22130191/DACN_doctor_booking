package com.example.doctorbooking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalRequest {
    private String name;
    private String address;
    private String phone;
    private String email;
}
