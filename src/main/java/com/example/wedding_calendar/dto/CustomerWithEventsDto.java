package com.example.wedding_calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerWithEventsDto {

    private Long customerId;
    private String husbandName;
    private String wifeName;
    private String makeupRehearsal;
    private String makeupWedding;
    private List<EventDto> events;
}
