package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.ResponseDTO;
import com.app.quantitymeasurement.entity.QuantityEntity;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.repository.QuantityRepository;
import com.app.quantitymeasurement.service.QuantityService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityController {

    @Autowired
    private QuantityService service;

    @Autowired
    private QuantityRepository repo;

    // ✅ MAIN API
    @PostMapping("/perform")
    public ResponseDTO performOperation(@Valid @RequestBody QuantityInputDTO inputDTO) {

        OperationType operation = inputDTO.getThisQuantityDTO().getOperationType();

        if (operation == null) {
            throw new RuntimeException("OperationType is required");
        }

        switch (operation) {
            case ADD:
                return service.add(inputDTO);
            case SUBTRACT:
                return service.subtract(inputDTO);
            case MULTIPLY:
                return service.multiply(inputDTO);
            case DIVIDE:
                return service.divide(inputDTO);
            case COMPARE:
                return service.compare(inputDTO);
            case CONVERT:
                return service.convert(inputDTO);
            default:
                throw new RuntimeException("Invalid Operation");
        }
    }

    // ✅ GET: History by operation
    @GetMapping("/history/operation/{operation}")
    public List<QuantityEntity> getByOperation(@PathVariable String operation) {
        return repo.findByOperation(operation);
    }

    // ✅ GET: History by type
    @GetMapping("/history/type/{type}")
    public List<QuantityEntity> getByType(@PathVariable String type) {
        return repo.findByType(type);
    }

    // ✅ GET: Count by operation
    @GetMapping("/count/{operation}")
    public long countByOperation(@PathVariable String operation) {
        return repo.countByOperation(operation);
    }

    // ✅ GET: Error history
    @GetMapping("/history/errored")
    public List<QuantityEntity> getErrors() {
        return repo.findByErrorTrue();
    }
}