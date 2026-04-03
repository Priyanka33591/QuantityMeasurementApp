package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.ResponseDTO;
import com.app.quantitymeasurement.entity.QuantityHistory;
import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.repository.HistoryRepository;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.service.QuantityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityController {


    private final QuantityService service;
    @Autowired
    public QuantityController(QuantityService service){
        this.service = service;
    }

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ MAIN API
    @PostMapping("/perform")
    public ResponseEntity<ResponseDTO> performOperation(@RequestBody QuantityInputDTO input) {

        OperationType operationType = input.getMeta().getOperationType();

        if (operationType == null) {
            throw new RuntimeException("OperationType is required");
        }

        ResponseDTO response = switch (operationType) {
            case ADD -> service.add(input);
            case SUBTRACT -> service.subtract(input);
            case MULTIPLY -> service.multiply(input);
            case DIVIDE -> service.divide(input);
            case COMPARE -> service.compare(input);
            case CONVERT -> service.convert(input);
        };

        return ResponseEntity.ok(response);
    }

    // ✅ Health check
    @GetMapping
    public String checkStartUP() {
        return "✅ Quantity Measurement App Running!";
    }

    //SIGNUP
    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists!");
        }

        if (userRepository.existsByEmail(user.getEmail().trim().toLowerCase())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        user.setEmail(user.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // 📊 USER-SPECIFIC HISTORY
    @GetMapping("/history")
    public ResponseEntity<List<QuantityHistory>> getHistory() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        List<QuantityHistory> history = historyRepository.findByUsername(username);

        return ResponseEntity.ok(history);
    }

    // 📊 OPTIONAL: LIMITED HISTORY (LATEST N)
    @GetMapping("/history/latest")
    public ResponseEntity<List<QuantityHistory>> getLatestHistory(
            @RequestParam(defaultValue = "5") int limit) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        List<QuantityHistory> history = historyRepository.findByUsername(username);

        // simple limit logic
        if (history.size() > limit) {
            history = history.subList(0, limit);
        }

        return ResponseEntity.ok(history);
    }
}