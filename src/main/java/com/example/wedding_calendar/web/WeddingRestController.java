package com.example.wedding_calendar.web;

import com.example.wedding_calendar.dto.CustomerWithEventsDto;
import com.example.wedding_calendar.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class WeddingRestController {

    private final CustomerService customerService;

    public WeddingRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public ResponseEntity<Map<String, Object>> getCustomerList() {

        List<CustomerWithEventsDto> customerList = customerService.getAllCustomersWithEvents();

        return ResponseEntity.of(Optional.of(Map.of(
                "success", true,
                "data", customerList
        )));

    }
}
