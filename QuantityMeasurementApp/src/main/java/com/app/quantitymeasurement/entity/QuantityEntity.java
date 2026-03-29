package com.app.quantitymeasurement.entity;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "quantity_measurements")
public class QuantityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double value1;
    private String unit1;
    private String type;
    private Double value2;
    private String unit2;
    private String operation;
    private Double result;
    private String resultUnit;
    private boolean error;
    private String errorMessage;
}