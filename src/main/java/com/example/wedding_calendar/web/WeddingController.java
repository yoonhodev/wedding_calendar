package com.example.wedding_calendar.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeddingController {

    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("/test")
    public String test() {

        return "test";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }
}
