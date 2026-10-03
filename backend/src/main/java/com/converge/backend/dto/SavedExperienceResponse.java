package com.converge.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SavedExperienceResponse {

    private Long id;
    private Long experienceId;
    private String title;
    private String category;
    private String genre;
    private String language;
    private String city;
    private String venueName;
    private Integer durationMinutes;
    private BigDecimal startingPrice;
    private String description;
    private String imageUrl;
    private LocalDateTime savedAt;

    public SavedExperienceResponse(
            Long id,
            Long experienceId,
            String title,
            String category,
            String genre,
            String language,
            String city,
            String venueName,
            Integer durationMinutes,
            BigDecimal startingPrice,
            String description,
            String imageUrl,
            LocalDateTime savedAt
    ) {
        this.id = id;
        this.experienceId = experienceId;
        this.title = title;
        this.category = category;
        this.genre = genre;
        this.language = language;
        this.city = city;
        this.venueName = venueName;
        this.durationMinutes = durationMinutes;
        this.startingPrice = startingPrice;
        this.description = description;
        this.imageUrl = imageUrl;
        this.savedAt = savedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public String getTitle() {
        return title;
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

    public String getCity() {
        return city;
    }

    public String getVenueName() {
        return venueName;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }
}