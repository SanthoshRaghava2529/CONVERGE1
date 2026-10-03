package com.converge.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.converge.backend.entity.Experience;
import com.converge.backend.service.ExperienceService;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    public List<Experience> getExperiences(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category
    ) {

        if (city != null && category != null) {
            return experienceService
                    .getExperiencesByCityAndCategory(city, category);
        }

        if (city != null) {
            return experienceService.getExperiencesByCity(city);
        }

        if (category != null) {
            return experienceService.getExperiencesByCategory(category);
        }

        return experienceService.getAllExperiences();
    }

    @GetMapping("/{id}")
    public Experience getExperienceById(
            @PathVariable Long id
    ) {
        return experienceService.getExperienceById(id);
    }
}