package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.ResponseDTO;

public interface QuantityService {

    ResponseDTO add(QuantityInputDTO inputDTO);
    ResponseDTO subtract(QuantityInputDTO inputDTO);
    ResponseDTO multiply(QuantityInputDTO inputDTO);
    ResponseDTO divide(QuantityInputDTO inputDTO);
    ResponseDTO compare(QuantityInputDTO inputDTO);
    ResponseDTO convert(QuantityInputDTO inputDTO);
<<<<<<< HEAD

=======
>>>>>>> 2db7cb5e0b5c1e3c8cc5a4e898bf544d3740b135
}