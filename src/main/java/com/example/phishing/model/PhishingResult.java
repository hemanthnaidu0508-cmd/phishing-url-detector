package com.example.phishing.model;

import java.util.List;

public class PhishingResult {

    private String url;
    private String verdict;
    private int riskScore;
    private List<String> reasons;

    public PhishingResult(String url, String verdict, int riskScore, List<String> reasons) {
        this.url = url;
        this.verdict = verdict;
        this.riskScore = riskScore;
        this.reasons = reasons;
    }

    public String getUrl() {
        return url;
    }

    public String getVerdict() {
        return verdict;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public List<String> getReasons() {
        return reasons;
    }
}
