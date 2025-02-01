package com.example.wedding_calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerRequestDto {
    private String userId;
    private String husbandName;
    private String wifeName;
    private List<EventRequestDto> events;
}
