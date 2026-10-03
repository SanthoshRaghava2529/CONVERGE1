package com.converge.backend.controller;

import com.converge.backend.dto.SavedExperienceResponse;
import com.converge.backend.security.JwtService;
import com.converge.backend.service.SavedExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved")
public class SavedExperienceController {

    private final SavedExperienceService savedExperienceService;
    private final JwtService jwtService;

    public SavedExperienceController(
            SavedExperienceService savedExperienceService,
            JwtService jwtService
    ) {
        this.savedExperienceService = savedExperienceService;
        this.jwtService = jwtService;
    }

    @PostMapping("/{experienceId}")
    public ResponseEntity<SavedExperienceResponse> saveExperience(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long experienceId
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        SavedExperienceResponse response =
                savedExperienceService.saveExperience(
                        email,
                        experienceId
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> unsaveExperience(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long experienceId
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        savedExperienceService.unsaveExperience(
                email,
                experienceId
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SavedExperienceResponse>> getSavedExperiences(
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return ResponseEntity.ok(
                savedExperienceService.getSavedExperiences(email)
        );
    }

    @GetMapping("/{experienceId}/status")
    public ResponseEntity<Boolean> isSaved(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long experienceId
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return ResponseEntity.ok(
                savedExperienceService.isSaved(
                        email,
                        experienceId
                )
        );
    }
}