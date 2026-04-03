package com.app.quantitymeasurement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class QuantityHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 INPUT 1
    private double inputValue1;
    private String unit1;

    // 🔹 INPUT 2 (optional)
    private Double inputValue2;
    private String unit2;

    // 🔹 RESULT
    private double result;
    private String resultUnit;

    // 🔹 META
    private String operationType;      // ADD, SUBTRACT etc
    private String measurementType;   // Length, Weight etc

    // 🔹 USER
    private String username;

    private LocalDateTime timestamp;
}