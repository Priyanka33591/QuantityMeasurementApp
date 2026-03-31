package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.ResponseDTO;
import com.app.quantitymeasurement.entity.QuantityEntity;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.repository.QuantityRepository;
import com.app.quantitymeasurement.service.QuantityService;

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
    public ResponseDTO performOperation(@RequestBody QuantityInputDTO input) {

        OperationType operationType = input.getMeta().getOperationType();

        if (operationType == null) {
            throw new RuntimeException("OperationType is required");
        }

        return switch (operationType) {
            case ADD -> service.add(input);
            case SUBTRACT -> service.subtract(input);
            case MULTIPLY -> service.multiply(input);
            case DIVIDE -> service.divide(input);
            case COMPARE -> service.compare(input);
            case CONVERT -> service.convert(input);
            default -> throw new RuntimeException("Invalid operation");
        };
    }

    @GetMapping
    public String checkStartUP(){
        return "Everything is Okay!";
    }

    @GetMapping("/history/operation/{operation}")
    public List<QuantityEntity> getByOperation(@PathVariable String operation) {
        return repo.findByOperation(operation);
    }

    @GetMapping("/history/type/{type}")
    public List<QuantityEntity> getByType(@PathVariable String type) {
        return repo.findByType(type);
    }

    @GetMapping("/count/{operation}")
    public long countByOperation(@PathVariable String operation) {
        return repo.countByOperation(operation);
    }

    @GetMapping("/history/errored")
    public List<QuantityEntity> getErrors() {
        return repo.findByErrorTrue();
    }
}


