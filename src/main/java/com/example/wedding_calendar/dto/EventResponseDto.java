package com.example.wedding_calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
@AllArgsConstructor
public class EventResponseDto {
    private Long idx;
    private String eventType;
    private String dDay;
    private Integer guide31Days;
    private Integer guide14Days;
    private Integer guide2Days;
    private String orderStatus;
}
