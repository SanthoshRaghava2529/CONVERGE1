package com.converge.backend.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PreferenceRequest {

    @NotBlank(message = "Category is required")
    @Size(max = 30, message = "Category cannot exceed 30 characters")
    private String category;

    @NotBlank(message = "Genre is required")
    @Size(max = 50, message = "Genre cannot exceed 50 characters")
    private String genre;

    @NotBlank(message = "Language is required")
    @Size(max = 50, message = "Language cannot exceed 50 characters")
    private String language;

    @NotNull(message = "Maximum budget is required")
    @Min(value = 1, message = "Maximum budget must be greater than zero")
    private Integer maxBudget;

    @NotNull(message = "Maximum duration is required")
    @Min(value = 1, message = "Maximum duration must be greater than zero")
    private Integer maxDurationMinutes;

    @NotNull(message = "Preferred start time is required")
    private LocalTime preferredStartTime;

    @NotNull(message = "Maximum distance is required")
    @Min(value = 1, message = "Maximum distance must be greater than zero")
    private Integer maxDistanceKm;

    public PreferenceRequest() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(Integer maxBudget) {
        this.maxBudget = maxBudget;
    }

    public Integer getMaxDurationMinutes() {
        return maxDurationMinutes;
    }

    public void setMaxDurationMinutes(Integer maxDurationMinutes) {
        this.maxDurationMinutes = maxDurationMinutes;
    }

    public LocalTime getPreferredStartTime() {
        return preferredStartTime;
    }

    public void setPreferredStartTime(LocalTime preferredStartTime) {
        this.preferredStartTime = preferredStartTime;
    }

    public Integer getMaxDistanceKm() {
        return maxDistanceKm;
    }

    public void setMaxDistanceKm(Integer maxDistanceKm) {
        this.maxDistanceKm = maxDistanceKm;
    }
}
