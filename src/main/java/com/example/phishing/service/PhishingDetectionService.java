package com.example.phishing.service;

import com.example.phishing.model.PhishingResult;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PhishingDetectionService {

    private static final String[] BRANDS = {
            "paypal", "google", "facebook", "amazon", "apple", "microsoft", "bank", "upi"
    };

    private static final String[] SUSPICIOUS_KEYWORDS = {
            "login", "verify", "secure", "update", "account", "free", "bonus", "click"
    };

    private static final String[] URL_SHORTENERS = {
            "bit.ly", "tinyurl", "t.co", "goo.gl"
    };

    private static final String[] SUSPICIOUS_TLDS = {
            ".xyz", ".tk", ".ml", ".ga", ".cf"
    };

    public PhishingResult analyze(String url) {

        int riskScore = 0;
        List<String> reasons = new ArrayList<>();

        try {
            URI uri = new URI(url);
            String host = uri.getHost() == null ? "" : uri.getHost().toLowerCase();

            /* 1️⃣ Long URL */
            if (url.length() > 75) {
                riskScore += 10;
                reasons.add("URL is unusually long");
            }

            /* 2️⃣ IP-based URL */
            if (Pattern.matches("https?://\\d+\\.\\d+\\.\\d+\\.\\d+.*", url)) {
                riskScore += 25;
                reasons.add("IP-based URL detected");
            }

            /* 3️⃣ '@' symbol trick */
            if (url.contains("@")) {
                riskScore += 20;
                reasons.add("URL contains '@' symbol (redirection trick)");
            }

            /* 4️⃣ Multiple '//' */
            if (url.indexOf("//") != url.lastIndexOf("//")) {
                riskScore += 10;
                reasons.add("Multiple '//' found in URL");
            }

            /* 5️⃣ URL encoding */
            if (url.matches(".*%[0-9a-fA-F]{2}.*")) {
                riskScore += 10;
                reasons.add("URL contains encoded characters");
            }

            /* 6️⃣ HTTPS missing */
            if (!url.startsWith("https://")) {
                riskScore += 15;
                reasons.add("HTTPS is missing");
            }

            /* 7️⃣ Brand impersonation */
            for (String brand : BRANDS) {
                if (host.contains(brand) && !host.endsWith(brand + ".com")) {
                    riskScore += 30;
                    reasons.add("Possible brand impersonation: " + brand);
                    break;
                }
            }

            /* 8️⃣ Suspicious keywords */
            for (String keyword : SUSPICIOUS_KEYWORDS) {
                if (url.toLowerCase().contains(keyword)) {
                    riskScore += 5;
                    reasons.add("Suspicious keyword detected: " + keyword);
                }
            }

            /* 9️⃣ URL shorteners */
            for (String shortener : URL_SHORTENERS) {
                if (host.contains(shortener)) {
                    riskScore += 25;
                    reasons.add("URL shortener detected");
                    break;
                }
            }

            /* 🔟 Suspicious TLDs */
            for (String tld : SUSPICIOUS_TLDS) {
                if (host.endsWith(tld)) {
                    riskScore += 20;
                    reasons.add("Suspicious domain extension: " + tld);
                }
            }

            /* 1️⃣1️⃣ Too many subdomains */
            if (host.split("\\.").length > 4) {
                riskScore += 15;
                reasons.add("Too many subdomains");
            }

        } catch (Exception e) {
            riskScore = 100;
            reasons.add("Invalid or malformed URL");
        }

        /* Verdict */
        String verdict;
        if (riskScore >= 60) {
            verdict = "PHISHING";
        } else if (riskScore >= 30) {
            verdict = "SUSPICIOUS";
        } else {
            verdict = "SAFE";
        }

        return new PhishingResult(url, verdict, riskScore, reasons);
    }
}
