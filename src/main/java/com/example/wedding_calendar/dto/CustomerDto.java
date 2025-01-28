package com.example.wedding_calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CustomerDto {
    private Long idx;
    private String husbandName;
    private String wifeName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
