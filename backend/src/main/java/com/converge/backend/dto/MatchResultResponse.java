package com.converge.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MatchResultResponse {

    private Long experienceId;
    private String title;
    private String category;
    private String genre;
    private String language;
    private String city;
    private String venueName;

    private Integer durationMinutes;
    private BigDecimal startingPrice;

    private Long scheduleId;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private double groupScore;
    private double averageMemberScore;
    private double minimumMemberScore;

    private List<String> reasons;
    private List<String> compromises;

    public MatchResultResponse() {
    }

    public MatchResultResponse(
            Long experienceId,
            String title,
            String category,
            String genre,
            String language,
            String city,
            String venueName,
            Integer durationMinutes,
            BigDecimal startingPrice,
            Long scheduleId,
            LocalDate scheduleDate,
            LocalTime startTime,
            LocalTime endTime,
            double groupScore,
            double averageMemberScore,
            double minimumMemberScore,
            List<String> reasons,
            List<String> compromises
    ) {
        this.experienceId = experienceId;
        this.title = title;
        this.category = category;
        this.genre = genre;
        this.language = language;
        this.city = city;
        this.venueName = venueName;
        this.durationMinutes = durationMinutes;
        this.startingPrice = startingPrice;
        this.scheduleId = scheduleId;
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.groupScore = groupScore;
        this.averageMemberScore = averageMemberScore;
        this.minimumMemberScore = minimumMemberScore;
        this.reasons = reasons;
        this.compromises = compromises;
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

    public Long getScheduleId() {
        return scheduleId;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public double getGroupScore() {
        return groupScore;
    }

    public double getAverageMemberScore() {
        return averageMemberScore;
    }

    public double getMinimumMemberScore() {
        return minimumMemberScore;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public List<String> getCompromises() {
        return compromises;
    }
}
