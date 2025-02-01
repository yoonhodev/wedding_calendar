package com.example.wedding_calendar.web;

import com.example.wedding_calendar.dto.CustomerRequestDto;
import com.example.wedding_calendar.dto.CustomerResponseDto;
import com.example.wedding_calendar.dto.CustomerWithEventsDto;
import com.example.wedding_calendar.entity.User;
import com.example.wedding_calendar.repository.UserRepository;
import com.example.wedding_calendar.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class WeddingRestController {

    private final CustomerService customerService;
    private final UserRepository userRepository;

    public WeddingRestController(CustomerService customerService, UserRepository userRepository) {
        this.customerService = customerService;
        this.userRepository = userRepository;
    }

    @GetMapping("/customer")
    public ResponseEntity<Map<String, Object>> getCustomerList() {

        List<CustomerWithEventsDto> customerList = customerService.getAllCustomersWithEvents();

        return ResponseEntity.of(Optional.of(Map.of(
                "success", true,
                "data", customerList
        )));
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerResponseDto> saveCustomer(@RequestBody CustomerRequestDto requestDto) {

        // userId로 User 객체 조회
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + requestDto.getUserId()));

        CustomerResponseDto responseDto = customerService.saveCustomer(requestDto, user);

        return ResponseEntity.ok(responseDto);
    }
}
