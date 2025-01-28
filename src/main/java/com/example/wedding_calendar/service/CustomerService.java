package com.example.wedding_calendar.service;

import com.example.wedding_calendar.dto.CustomerWithEventsDto;
import com.example.wedding_calendar.dto.EventDto;
import com.example.wedding_calendar.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerWithEventsDto> getAllCustomersWithEvents() {
        return customerRepository.findAll().stream().map(customer ->
                new CustomerWithEventsDto(
                        customer.getIdx(),
                        customer.getHusbandName(),
                        customer.getWifeName(),
                        customer.getEvents().stream()
                                .map(event -> new EventDto(
                                        event.getIdx(),
                                        event.getEventType(),
                                        event.getDDay(),
                                        event.getGuide31Days(),
                                        event.getGuide14Days(),
                                        event.getGuide2Days(),
                                        event.getOrderStatus()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
