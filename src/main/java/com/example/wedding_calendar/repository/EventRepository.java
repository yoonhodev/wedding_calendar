package com.example.wedding_calendar.repository;

import com.example.wedding_calendar.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
