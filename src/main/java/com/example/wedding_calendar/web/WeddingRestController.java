package com.example.wedding_calendar.web;

import com.example.wedding_calendar.dto.*;
import com.example.wedding_calendar.entity.Customer;
import com.example.wedding_calendar.entity.User;
import com.example.wedding_calendar.repository.CustomerRepository;
import com.example.wedding_calendar.repository.UserRepository;
import com.example.wedding_calendar.security.JwtTokenProvider;
import com.example.wedding_calendar.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WeddingRestController {

    private final CustomerService customerService;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public WeddingRestController(CustomerService customerService, UserRepository userRepository, CustomerRepository customerRepository, JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/customer")
    public ResponseEntity<Map<String, Object>> getCustomerList(@RequestHeader("Authorization") String token) {

        String userId = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));

        List<CustomerWithEventsDto> customerList = customerService.getCustomersByUserId(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", customerList);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerResponseDto> saveCustomer(@RequestBody CustomerRequestDto requestDto) {

        // userId로 User 객체 조회
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + requestDto.getUserId()));

        CustomerResponseDto responseDto = customerService.saveCustomer(requestDto, user);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/makeup")
    public ResponseEntity<?> saveMakeup(@RequestBody MakeupRequestDto requestDto) {

        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + requestDto.getCustomerId()));

        customerService.saveMakeup(requestDto, customer);

        return ResponseEntity.ok().body(Map.of("message", "메이크업 정보가 저장되었습니다."));
    }
}
