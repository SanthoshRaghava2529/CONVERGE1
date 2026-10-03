package com.converge.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.converge.backend.entity.Experience;
import com.converge.backend.repository.ExperienceRepository;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public List<Experience> getAllExperiences() {
        return experienceRepository.findByActiveTrue();
    }

    public Experience getExperienceById(Long id) {
        return experienceRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Experience not found"
                        )
                );
    }

    public List<Experience> getExperiencesByCity(String city) {
        return experienceRepository.findByCityIgnoreCaseAndActiveTrue(city);
    }

    public List<Experience> getExperiencesByCategory(String category) {
        return experienceRepository.findByCategoryIgnoreCaseAndActiveTrue(category);
    }

    public List<Experience> getExperiencesByCityAndCategory(
            String city,
            String category
    ) {
        return experienceRepository
                .findByCityIgnoreCaseAndCategoryIgnoreCaseAndActiveTrue(
                        city,
                        category
                );
    }
}