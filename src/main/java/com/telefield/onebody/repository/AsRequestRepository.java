package com.telefield.onebody.repository;

import com.telefield.onebody.entity.AsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsRequestRepository extends JpaRepository<AsRequest, Long> {
    List<AsRequest> findByManagerIdOrderByRegDate(String managerId);
}
