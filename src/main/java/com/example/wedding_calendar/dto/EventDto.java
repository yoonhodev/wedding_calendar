package com.example.wedding_calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EventDto {
    private Long idx;
    private String eventType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // 날짜 형식 지정
    private String dDay;
    private Integer guide31Days;
    private Integer guide14Days;
    private Integer guide2Days;
    private String orderStatus;
}
