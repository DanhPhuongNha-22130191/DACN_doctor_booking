package com.example.doctorbooking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    DOCTOR_NOT_FOUND(1002, "Doctor not found", HttpStatus.NOT_FOUND),
    APPOINTMENT_NOT_FOUND(1003, "Appointment not found", HttpStatus.NOT_FOUND),
    HOSPITAL_NOT_FOUND(1004, "Hospital not found", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_FOUND(1005, "Department not found", HttpStatus.NOT_FOUND),
    INVALID_APPOINTMENT_DATE(1006, "Appointment date must be in the future", HttpStatus.BAD_REQUEST),
    INVALID_REGISTER_REQUEST(1007, "Invalid register request", HttpStatus.BAD_REQUEST),
    USERNAME_REQUIRED(1008, "Username is required", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1009, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(1010, "Password is required", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1011, "Password must be at least 6 characters", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(1012, "Email is required", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1013, "Email is invalid", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS(1014, "Username already exists", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS(1015, "Email already exists", HttpStatus.CONFLICT),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
