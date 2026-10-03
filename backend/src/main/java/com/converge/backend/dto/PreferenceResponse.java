package com.converge.backend.dto;

import java.time.LocalTime;

public class PreferenceResponse {

    private Long id;
    private Long groupMemberId;

    private String category;
    private String genre;
    private String language;

    private Integer maxBudget;
    private Integer maxDurationMinutes;
    private LocalTime preferredStartTime;
    private Integer maxDistanceKm;

    public PreferenceResponse() {
    }

    public PreferenceResponse(
            Long id,
            Long groupMemberId,
            String category,
            String genre,
            String language,
            Integer maxBudget,
            Integer maxDurationMinutes,
            LocalTime preferredStartTime,
            Integer maxDistanceKm
    ) {
        this.id = id;
        this.groupMemberId = groupMemberId;
        this.category = category;
        this.genre = genre;
        this.language = language;
        this.maxBudget = maxBudget;
        this.maxDurationMinutes = maxDurationMinutes;
        this.preferredStartTime = preferredStartTime;
        this.maxDistanceKm = maxDistanceKm;
    }

    public Long getId() {
        return id;
    }

    public Long getGroupMemberId() {
        return groupMemberId;
    }

    public String getCategory() {
        return category;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getMaxBudget() {
        return maxBudget;
    }

    public Integer getMaxDurationMinutes() {
        return maxDurationMinutes;
    }

    public LocalTime getPreferredStartTime() {
        return preferredStartTime;
    }

    public Integer getMaxDistanceKm() {
        return maxDistanceKm;
    }
}
