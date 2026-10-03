package com.converge.backend.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public Map<String, Object> test(Authentication authentication) {

        return Map.of(
                "message", "JWT authentication is working",
                "authenticated", true,
                "email", authentication.getName()
        );
    }
}
