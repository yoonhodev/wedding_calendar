package com.example.wedding_calendar.repository;


import com.example.wedding_calendar.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
