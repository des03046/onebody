package com.telefield.onebody.repository;

import com.telefield.onebody.entity.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GatewayRepository extends JpaRepository<Gateway, String> {
    Optional<Gateway> findByGwMacAddress(String macAddress);

    List<Gateway> findByManagerIdOrChildId(String managerId, String childId);
}
