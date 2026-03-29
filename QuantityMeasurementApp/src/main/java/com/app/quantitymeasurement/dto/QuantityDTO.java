package com.app.quantitymeasurement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.app.quantitymeasurement.model.OperationType;

@Data
public class QuantityDTO {

    private OperationType operationType;

    @NotNull
    private Double value;

    @NotNull
    private String unit;

    @NotNull
    private String measurementType; // LengthUnit, WeightUnit etc
}