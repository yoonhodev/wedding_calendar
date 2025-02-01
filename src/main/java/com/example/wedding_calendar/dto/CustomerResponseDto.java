package com.example.wedding_calendar.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {
    private Long customerId;
    private String husbandName;
    private String wifeName;
    private List<EventResponseDto> events;
}
