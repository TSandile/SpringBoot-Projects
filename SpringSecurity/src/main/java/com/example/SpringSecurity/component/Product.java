package com.example.SpringSecurity.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;
    private String name;
    private Double price;
    private int quantity;
}
