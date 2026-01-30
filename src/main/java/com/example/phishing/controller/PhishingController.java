package com.example.phishing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhishingController {

    @GetMapping("/")
    public String home() {
        return "index"; // loads templates/index.html
    }
}
