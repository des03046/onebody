package com.telefield.onebody.repository;

import com.telefield.onebody.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByGwMacAddressOrderByRegDate(String macAddress);

    List<Event> findByRegDateAfterOrderByRegDateDesc(LocalDateTime regDate);

    List<Event> findByRegDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
