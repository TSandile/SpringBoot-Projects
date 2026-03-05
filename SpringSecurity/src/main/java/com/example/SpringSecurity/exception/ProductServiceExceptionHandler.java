package com.example.SpringSecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductServiceExceptionHandler {

//    @ExceptionHandler(ProductNotFoundException.class)
//    public ErrorDto handleProductNotFoundException(ProductNotFoundException exception){
//        return ErrorDto.builder()
//                .status("Fail")
//                .errorMessage(exception.getMessage())
//                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                .build();
//    }

    //Make an error response using the ProblemDetail class
    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFoundException(ProductNotFoundException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage());
    }
}
