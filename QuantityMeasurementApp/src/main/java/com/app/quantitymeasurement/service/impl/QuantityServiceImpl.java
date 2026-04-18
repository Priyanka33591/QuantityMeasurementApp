package com.app.quantitymeasurement.service.impl;

import com.app.quantitymeasurement.dto.*;
import com.app.quantitymeasurement.entity.QuantityEntity;
import com.app.quantitymeasurement.entity.QuantityHistory;
import com.app.quantitymeasurement.repository.HistoryRepository;
import com.app.quantitymeasurement.repository.QuantityRepository;
import com.app.quantitymeasurement.service.QuantityService;
import com.app.quantitymeasurement.unit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QuantityServiceImpl implements QuantityService {

    @Autowired
    private QuantityRepository repo;

    @Autowired
    private HistoryRepository historyRepository;

    // 🔹 UNIT HELPER
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

    private InputDTO q1(QuantityInputDTO input) {
        return input.getInput1();
    }

    private InputDTO q2(QuantityInputDTO input) {
        return input.getInput2();
    }

    private MetaDTO meta(QuantityInputDTO input) {
        return input.getMeta();
    }

    private String outputUnit(MetaDTO meta, InputDTO q1) {
        return (meta.getResultUnit() != null && !meta.getResultUnit().isEmpty())
                ? meta.getResultUnit()
                : q1.getUnit();
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    private void save(QuantityEntity e, InputDTO q1, InputDTO q2,
            String op, double result, String unit, String type) {

        e.setValue1(q1.getValue());
        e.setUnit1(q1.getUnit());
        e.setValue2(q2 != null ? q2.getValue() : 0);
        e.setUnit2(q2 != null ? q2.getUnit() : null);
        e.setType(type);
        e.setOperation(op);
        e.setResult(result);
        e.setResultUnit(unit);
        e.setError(false);

        repo.save(e);
    }

    // 🔥 COMMON HISTORY METHOD
    private void saveHistory(QuantityInputDTO input, double result, String resultUnit) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || "anonymousUser".equals(auth.getName())) {
            return;
        }

        InputDTO q1 = input.getInput1();
        InputDTO q2 = input.getInput2();
        MetaDTO meta = input.getMeta();

        String username = auth.getName();

        QuantityHistory history = new QuantityHistory();

        // input 1
        history.setInputValue1(q1.getValue());
        history.setUnit1(q1.getUnit());

        // input 2 (optional)
        if (q2 != null) {
            history.setInputValue2(q2.getValue());
            history.setUnit2(q2.getUnit());
        }

        // result
        history.setResult(result);
        history.setResultUnit(resultUnit);

        // meta
        history.setOperationType(meta.getOperationType().name());
        history.setMeasurementType(meta.getMeasurementType());

        // user
        history.setUsername(username);

        history.setTimestamp(LocalDateTime.now());

        historyRepository.save(history);
    }

    // ✅ ADD
    @Override
    public ResponseDTO add(QuantityInputDTO input) {

        InputDTO q1 = q1(input);
        InputDTO q2 = q2(input);
        MetaDTO meta = meta(input);

        double resultBase = toBase(q1.getValue(), q1.getUnit(), meta.getMeasurementType()) +
                toBase(q2.getValue(), q2.getUnit(), meta.getMeasurementType());

        String unit = outputUnit(meta, q1);

        double result = getUnit(unit, meta.getMeasurementType()).fromBase(resultBase);

        result = round(result);

        save(new QuantityEntity(), q1, q2, "ADD", result, unit, meta.getMeasurementType());

        saveHistory(input, result, unit);

        return new ResponseDTO(result, unit);
    }

    // ✅ SUBTRACT
    @Override
    public ResponseDTO subtract(QuantityInputDTO input) {

        InputDTO q1 = q1(input);
        InputDTO q2 = q2(input);
        MetaDTO meta = meta(input);

        double resultBase = toBase(q1.getValue(), q1.getUnit(), meta.getMeasurementType()) -
                toBase(q2.getValue(), q2.getUnit(), meta.getMeasurementType());

        String unit = outputUnit(meta, q1);

        double result = getUnit(unit, meta.getMeasurementType()).fromBase(resultBase);

        result = round(result);

        save(new QuantityEntity(), q1, q2, "SUBTRACT", result, unit, meta.getMeasurementType());

        saveHistory(input, result, unit);

        return new ResponseDTO(result, unit);
    }

    // MULTIPLY
    @Override
    public ResponseDTO multiply(QuantityInputDTO input) {

        InputDTO q1 = q1(input);
        InputDTO q2 = q2(input);

        if (!q1.getUnit().equals(q2.getUnit())) {
            throw new IllegalArgumentException("Units must be same for multiplication");
        }

        double result = q1.getValue() * q2.getValue();
        result = round(result);

        // 🔥 unit squared
        String unit = q1.getUnit() + "²";

        saveHistory(input, result, unit);

        return new ResponseDTO(result, unit);
    }

    // DIVIDE
    @Override
    public ResponseDTO divide(QuantityInputDTO input) {

        InputDTO q1 = q1(input);
        InputDTO q2 = q2(input);
        MetaDTO meta = meta(input);

        double base1 = toBase(q1.getValue(), q1.getUnit(), meta.getMeasurementType());
        double base2 = toBase(q2.getValue(), q2.getUnit(), meta.getMeasurementType());

        if (base2 == 0) {
            throw new ArithmeticException("Divide by zero");
        }

        double result = base1 / base2;

        result = round(result);

        save(new QuantityEntity(), q1, q2, "DIVIDE", result, "SCALAR", meta.getMeasurementType());

        saveHistory(input, result, "SCALAR");

        return new ResponseDTO(result, "SCALAR");
    }

    // ✅ CONVERT
    @Override
    public ResponseDTO convert(QuantityInputDTO input) {

        InputDTO q1 = q1(input);
        MetaDTO meta = meta(input);

        double base = toBase(q1.getValue(), q1.getUnit(), meta.getMeasurementType());

        String unit = meta.getResultUnit();

        if (unit == null || unit.isEmpty()) {
            throw new IllegalArgumentException("resultUnit is required");
        }

        double result = getUnit(unit, meta.getMeasurementType()).fromBase(base);
        result = round(result);

        save(new QuantityEntity(), q1, null, "CONVERT", result, unit, meta.getMeasurementType());

        saveHistory(input, result, unit);

        return new ResponseDTO(result, unit);
    }

    // ✅ COMPARE
    @Override
    public ResponseDTO compare(QuantityInputDTO input) {

        InputDTO q1 = q1(input);
        InputDTO q2 = q2(input);
        MetaDTO meta = meta(input);

        double v1 = toBase(q1.getValue(), q1.getUnit(), meta.getMeasurementType());
        double v2 = toBase(q2.getValue(), q2.getUnit(), meta.getMeasurementType());

        boolean equal = Math.abs(v1 - v2) < 0.0001;

        save(new QuantityEntity(), q1, q2, "COMPARE",
                equal ? 1 : 0, "BOOLEAN", meta.getMeasurementType());

        saveHistory(input, equal ? 1 : 0, "BOOLEAN");

        return new ResponseDTO(equal ? 1 : 0, equal ? "true" : "false");
    }
}