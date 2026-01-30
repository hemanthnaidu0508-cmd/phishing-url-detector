package com.example.phishing.controller;

import com.example.phishing.model.PhishingResult;
import com.example.phishing.service.PhishingDetectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhishingController {

    private final PhishingDetectionService detectionService;

    public PhishingController(PhishingDetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/scan")   // ✅ ONLY ONE PostMapping
    public String scanUrl(@RequestParam String url, Model model) {

        try {
            PhishingResult result = detectionService.analyze(url);
            model.addAttribute("result", result);
            model.addAttribute("url", url);
        } catch (Exception e) {
            model.addAttribute("error", "Invalid URL. Please enter a valid URL.");
        }

        return "index";
    }
}
