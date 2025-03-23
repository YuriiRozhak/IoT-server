package com.shpeiser.iotserver.repository;

import com.shpeiser.iotserver.model.SensorComparator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorComparatorRepository extends JpaRepository<SensorComparator, Long> {
}