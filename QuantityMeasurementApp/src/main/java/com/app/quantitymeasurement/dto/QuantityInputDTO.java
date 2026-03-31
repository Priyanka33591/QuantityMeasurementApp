package com.app.quantitymeasurement.dto;

import lombok.Data;


@Data
public class QuantityInputDTO {
    private InputDTO input1;
    private InputDTO input2; // optional for convert
    private MetaDTO meta;
}