package com.app.quantitymeasurement.dto;

import lombok.Data;

@Data
public class QuantityInputDTO {

    private QuantityDTO thisQuantityDTO; //first value
    private QuantityDTO thatQuantityDTO;

}