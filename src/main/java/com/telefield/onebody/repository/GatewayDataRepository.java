package com.telefield.onebody.repository;

import com.telefield.onebody.entity.GatewayData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GatewayDataRepository extends JpaRepository<GatewayData, Long> {
    Optional<GatewayData> findTopByGwMacAddrOrderByRegDateDesc(String macAddress);

    List<GatewayData> findByGwMacAddrOrderByRegDate(String macAddress);
}
