package com.bridgelabz.quantitymeasurement.repository;

import com.bridgelabz.quantitymeasurement.model.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMeasurementRepository extends JpaRepository<MeasurementEntity, Long> { }
