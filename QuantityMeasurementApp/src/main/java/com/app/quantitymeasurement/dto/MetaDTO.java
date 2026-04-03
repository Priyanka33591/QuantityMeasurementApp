package com.app.quantitymeasurement.dto;

import com.app.quantitymeasurement.model.OperationType;
import lombok.Data;

@Data
public class MetaDTO {

    private String measurementType;

    private OperationType operationType;

    private String resultUnit;
}