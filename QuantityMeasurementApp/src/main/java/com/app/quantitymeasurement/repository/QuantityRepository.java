package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityRepository extends JpaRepository<QuantityEntity, Long> {

    List<QuantityEntity> findByOperation(String operation);

    List<QuantityEntity> findByType(String type);

    long countByOperation(String operation);

    List<QuantityEntity> findByErrorTrue();
}