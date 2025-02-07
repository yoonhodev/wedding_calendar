package com.example.wedding_calendar.service;

import com.example.wedding_calendar.dto.*;
import com.example.wedding_calendar.entity.Customer;
import com.example.wedding_calendar.entity.Event;
import com.example.wedding_calendar.entity.User;
import com.example.wedding_calendar.repository.CustomerRepository;
import com.example.wedding_calendar.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;

    public List<CustomerWithEventsDto> getCustomersByUserId(String userId) {
        return customerRepository.findByUserId(userId).stream()
                .map(customer -> new CustomerWithEventsDto(
                        customer.getIdx(),
                        customer.getHusbandName(),
                        customer.getWifeName(),
                        customer.getMakeupRehearsal(),
                        customer.getMakeupWedding(),
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

    public CustomerResponseDto saveCustomer(CustomerRequestDto requestDto,  User user) {
        Customer customer = Customer.builder()
                .userId(user.getId())
                .husbandName(requestDto.getHusbandName())
                .wifeName(requestDto.getWifeName())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        List<Event> events = requestDto.getEvents().stream()
                .map(EventDto -> Event.builder()
                        .customer(savedCustomer)
                        .eventType(EventDto.getEventType())
                        .dDay(EventDto.getEventDate())
                        .build())
                .toList();

        eventRepository.saveAll(events);
        savedCustomer.setEvents(events);

        return convertToResponseDto(savedCustomer);
    }

    private CustomerResponseDto convertToResponseDto(Customer customer) {
        List<EventResponseDto> eventDtos = customer.getEvents().stream()
                .map(event -> EventResponseDto.builder()
                        .idx(event.getIdx())
                        .eventType(event.getEventType())
                        .dDay(event.getDDay().toString())
                        .guide31Days(event.getGuide31Days())
                        .guide14Days(event.getGuide14Days())
                        .guide2Days(event.getGuide2Days())
                        .orderStatus(event.getOrderStatus())
                        .build())
                .collect(Collectors.toList());

        return CustomerResponseDto.builder()
                .customerId(customer.getIdx())
                .husbandName(customer.getHusbandName())
                .wifeName(customer.getWifeName())
                .events(eventDtos)
                .build();
    }

    public void saveMakeup(MakeupRequestDto requestDto, Customer customer) {
        if("rehearsal".equals(requestDto.getType())) {
            customer.setMakeupRehearsal(requestDto.getValue());
        } else if("wedding".equals(requestDto.getType())) {
            customer.setMakeupWedding(requestDto.getValue());
        } else {
            throw new IllegalArgumentException("잘못된 메이크업 타입 :" + requestDto.getType());
        }

        customerRepository.save(customer);

    }
}
