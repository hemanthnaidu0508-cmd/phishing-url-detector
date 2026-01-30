package com.example.phishing.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityHeadersFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ BYPASS STATIC RESOURCES (VERY IMPORTANT)
        if (path.startsWith("/favicon")
                || path.startsWith("/style.css")
                || path.startsWith("/static")
                || path.endsWith(".png")
                || path.endsWith(".ico")
                || path.endsWith(".css")
                || path.endsWith(".js")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 🔒 existing security headers logic BELOW
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");

        filterChain.doFilter(request, response);
    }

}
