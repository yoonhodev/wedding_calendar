package com.example.wedding_calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class LoginRequestDto {
    private String id;
    private String pw;
}
