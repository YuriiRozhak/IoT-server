package com.shpeiser.iotserver.repository;

import com.shpeiser.iotserver.model.Actuator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActuatorRepository extends JpaRepository<Actuator, Long> {
}