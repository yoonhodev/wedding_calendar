package com.example.wedding_calendar.repository;

import com.example.wedding_calendar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id); // 추가: id로 조회
}
