package com.app.quantitymeasurement.service.impl;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.ResponseDTO;
import com.app.quantitymeasurement.entity.QuantityEntity;
import com.app.quantitymeasurement.repository.QuantityRepository;
import com.app.quantitymeasurement.service.QuantityService;
import com.app.quantitymeasurement.unit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityServiceImpl implements QuantityService {

    @Autowired
    private QuantityRepository repo;

    // 🔹 Helper method
    private IMeasurable getUnit(String unit, String type) {
        return switch (type) {
            case "LengthUnit" -> LengthUnit.valueOf(unit);
            case "WeightUnit" -> WeightUnit.valueOf(unit);
            case "VolumeUnit" -> VolumeUnit.valueOf(unit);
            case "TemperatureUnit" -> TemperatureUnit.valueOf(unit);
            default -> throw new IllegalArgumentException("Invalid type");
        };
    }

    private double toBase(double value, String unit, String type) {
        return getUnit(unit, type).toBase(value);
    }

    private QuantityDTO getThis(QuantityInputDTO input) {
        return input.getThisQuantityDTO();
    }

    private QuantityDTO getThat(QuantityInputDTO input) {
        return input.getThatQuantityDTO();
    }

    // 🔥 COMMON SAVE METHOD
    private void saveSuccess(QuantityEntity e, QuantityDTO q1, QuantityDTO q2, String op, double result, String unit) {
        e.setValue1(q1.getValue());
        e.setUnit1(q1.getUnit());
        e.setType(q1.getMeasurementType());
        e.setValue2(q2.getValue());
        e.setUnit2(q2.getUnit());
        e.setOperation(op);
        e.setResult(result);
        e.setResultUnit(unit);
        e.setError(false);
        repo.save(e);
    }

    private void saveError(QuantityEntity e, String op, Exception ex) {
        e.setOperation(op);
        e.setError(true);
        e.setErrorMessage(ex.getMessage());
        repo.save(e);
    }

    // ✅ ADD
    @Override
    public ResponseDTO add(QuantityInputDTO input) {

        QuantityEntity e = new QuantityEntity();
        QuantityDTO q1 = getThis(input);
        QuantityDTO q2 = getThat(input);

        try {
            double base1 = toBase(q1.getValue(), q1.getUnit(), q1.getMeasurementType());
            double base2 = toBase(q2.getValue(), q2.getUnit(), q2.getMeasurementType());
            double resultBase = base1 + base2;
            IMeasurable unit = getUnit(q1.getUnit(), q1.getMeasurementType());
            double result = unit.fromBase(resultBase);
            result = Math.round(result * 100.0) / 100.0;
            saveSuccess(e, q1, q2, "ADD", result, q1.getUnit());
            return new ResponseDTO(result, q1.getUnit());
        } catch (Exception ex) {
            saveError(e, "ADD", ex);
            throw ex;
        }
    }

    // ✅ SUBTRACT
    @Override
    public ResponseDTO subtract(QuantityInputDTO input) {

        QuantityEntity e = new QuantityEntity();
        QuantityDTO q1 = getThis(input);
        QuantityDTO q2 = getThat(input);

        try {
            double base1 = toBase(q1.getValue(), q1.getUnit(), q1.getMeasurementType());
            double base2 = toBase(q2.getValue(), q2.getUnit(), q2.getMeasurementType());

            double resultBase = base1 - base2;

            IMeasurable unit = getUnit(q1.getUnit(), q1.getMeasurementType());
            double result = unit.fromBase(resultBase);

            result = Math.round(result * 100.0) / 100.0;

            saveSuccess(e, q1, q2, "SUBTRACT", result, q1.getUnit());

            return new ResponseDTO(result, q1.getUnit());

        } catch (Exception ex) {
            saveError(e, "SUBTRACT", ex);
            throw ex;
        }
    }

    // ✅ MULTIPLY
    @Override
    public ResponseDTO multiply(QuantityInputDTO input) {

        QuantityEntity e = new QuantityEntity();
        QuantityDTO q1 = getThis(input);
        QuantityDTO q2 = getThat(input);

        try {
            double base1 = toBase(q1.getValue(), q1.getUnit(), q1.getMeasurementType());
            double base2 = toBase(q2.getValue(), q2.getUnit(), q2.getMeasurementType());

            double result = base1 * base2;

            result = Math.round(result * 100.0) / 100.0;

            saveSuccess(e, q1, q2, "MULTIPLY", result, q1.getUnit());

            return new ResponseDTO(result, q1.getUnit());

        } catch (Exception ex) {
            saveError(e, "MULTIPLY", ex);
            throw ex;
        }
    }

    // ✅ DIVIDE
    @Override
    public ResponseDTO divide(QuantityInputDTO input) {

        QuantityEntity e = new QuantityEntity();
        QuantityDTO q1 = getThis(input);
        QuantityDTO q2 = getThat(input);

        if (!q1.getMeasurementType().equals(q2.getMeasurementType())) {
            throw new IllegalArgumentException("Different measurement types cannot be multiplied");
        }

        try {
            if (q2.getValue() == 0) {
                throw new ArithmeticException("Divide by zero");
            }

            double base1 = toBase(q1.getValue(), q1.getUnit(), q1.getMeasurementType());
            double base2 = toBase(q2.getValue(), q2.getUnit(), q2.getMeasurementType());

            double result = base1 / base2;

            result = Math.round(result * 100.0) / 100.0;

            saveSuccess(e, q1, q2, "DIVIDE", result, q1.getUnit());

            return new ResponseDTO(result, q1.getUnit());

        } catch (Exception ex) {
            saveError(e, "DIVIDE", ex);
            throw ex;
        }
    }

    // ✅ COMPARE
    @Override
    public ResponseDTO compare(QuantityInputDTO input) {

        QuantityEntity e = new QuantityEntity();
        QuantityDTO q1 = getThis(input);
        QuantityDTO q2 = getThat(input);

        try {
            double v1 = toBase(q1.getValue(), q1.getUnit(), q1.getMeasurementType());
            double v2 = toBase(q2.getValue(), q2.getUnit(), q2.getMeasurementType());

            boolean isEqual = Math.abs(v1 - v2) < 0.0001;

            saveSuccess(e, q1, q2, "COMPARE", isEqual ? 1 : 0, "BOOLEAN");

            return new ResponseDTO(isEqual ? 1 : 0, isEqual ? "true" : "false");

        } catch (Exception ex) {
            saveError(e, "COMPARE", ex);
            throw ex;
        }
    }

    // ✅ CONVERT
    @Override
    public ResponseDTO convert(QuantityInputDTO input) {

        QuantityEntity e = new QuantityEntity();
        QuantityDTO q1 = getThis(input);
        QuantityDTO q2 = getThat(input);

        try {
            double base = toBase(q1.getValue(), q1.getUnit(), q1.getMeasurementType());

            IMeasurable target = getUnit(q2.getUnit(), q1.getMeasurementType());

            double result = target.fromBase(base);

            result = Math.round(result * 100.0) / 100.0;

            saveSuccess(e, q1, q2, "CONVERT", result, q2.getUnit());

            return new ResponseDTO(result, q2.getUnit());

        } catch (Exception ex) {
            saveError(e, "CONVERT", ex);
            throw ex;
        }
    }
}