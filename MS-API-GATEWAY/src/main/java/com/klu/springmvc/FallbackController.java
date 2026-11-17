package com.klu.springmvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping
    public Map<String, Object> getFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 503);
        response.put("message", "Service is currently unavailable. Please try again later.");
        return response;
    }

    @PostMapping
    public Map<String, Object> postFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 503);
        response.put("message", "Service is currently unavailable. Please try again later.");
        return response;
    }
}