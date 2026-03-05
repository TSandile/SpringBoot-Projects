package com.example.SpringSecurity.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorDto {
    public String status;
    public String errorMessage;
    public HttpStatus httpStatus;
}
