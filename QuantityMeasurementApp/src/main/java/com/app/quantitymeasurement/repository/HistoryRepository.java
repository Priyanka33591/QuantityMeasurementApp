package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<QuantityHistory, Long> {

    // 🔥 ONLY USER DATA
    List<QuantityHistory> findByUsername(String username);
}