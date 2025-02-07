package com.example.wedding_calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MakeupRequestDto {
    private Long customerId;
    private String type;
    private String value;
}
