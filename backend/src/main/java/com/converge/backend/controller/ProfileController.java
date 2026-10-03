package com.converge.backend.controller;

import com.converge.backend.dto.ProfileResponse;
import com.converge.backend.dto.ProfileUpdateRequest;
import com.converge.backend.service.ProfileService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                profileService.getProfile(email)
        );
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody ProfileUpdateRequest request
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                profileService.updateProfile(email, request)
        );
    }
}
