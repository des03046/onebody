package com.telefield.onebody.repository;

import com.telefield.onebody.entity.AsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsRequestRepository extends JpaRepository<AsRequest, Long> {
}
