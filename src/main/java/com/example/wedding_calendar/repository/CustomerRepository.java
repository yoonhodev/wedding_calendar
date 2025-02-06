package com.example.wedding_calendar.repository;


import com.example.wedding_calendar.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByUserId(String userId);
}
