package com.example.phishing.controller;

import com.example.phishing.model.PhishingResult;
import com.example.phishing.model.ScanRequest;
import com.example.phishing.service.PhishingDetectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PhishingApiController {

    private final PhishingDetectionService detectionService;

    public PhishingApiController(PhishingDetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @PostMapping("/scan")
    public ResponseEntity<PhishingResult> scanUrl(
            @Valid @RequestBody ScanRequest request) {

        PhishingResult result =
                detectionService.analyze(request.getUrl());

        return ResponseEntity.ok(result);
    }
}
