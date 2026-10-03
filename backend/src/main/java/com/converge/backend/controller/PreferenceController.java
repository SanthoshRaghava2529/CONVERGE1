package com.converge.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.converge.backend.dto.PreferenceRequest;
import com.converge.backend.dto.PreferenceResponse;
import com.converge.backend.security.JwtService;
import com.converge.backend.service.PreferenceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class PreferenceController {

    private final PreferenceService preferenceService;
    private final JwtService jwtService;

    public PreferenceController(
            PreferenceService preferenceService,
            JwtService jwtService
    ) {
        this.preferenceService = preferenceService;
        this.jwtService = jwtService;
    }

    @PostMapping("/{groupCode}/preferences")
    public ResponseEntity<PreferenceResponse> savePreference(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupCode,
            @Valid @RequestBody PreferenceRequest request
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        PreferenceResponse response =
                preferenceService.savePreference(
                        email,
                        groupCode,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{groupCode}/preferences/me")
    public ResponseEntity<PreferenceResponse> getMyPreference(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupCode
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        PreferenceResponse response =
                preferenceService.getMyPreference(
                        email,
                        groupCode
                );

        return ResponseEntity.ok(response);
    }
}
