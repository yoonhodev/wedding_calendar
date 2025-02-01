package com.example.wedding_calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRequestDto {
    private String eventType;
    private String eventDate;
}
