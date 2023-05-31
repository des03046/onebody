package com.telefield.onebody.repository;

import com.telefield.onebody.entity.GatewayCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatewayCycleRepository extends JpaRepository<GatewayCycle, Long> {
    List<GatewayCycle> findByGwMacAddressOrderByRegDate(String macAddress);
}
