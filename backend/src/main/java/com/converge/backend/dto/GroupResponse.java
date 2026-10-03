package com.converge.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GroupResponse {

    private Long id;
    private String groupCode;
    private String name;
    private String city;
    private LocalDate eventDate;
    private Integer maxBudget;

    private Long creatorId;
    private String creatorName;

    private String status;
    private LocalDateTime createdAt;

    public GroupResponse() {
    }

    public GroupResponse(
            Long id,
            String groupCode,
            String name,
            String city,
            LocalDate eventDate,
            Integer maxBudget,
            Long creatorId,
            String creatorName,
            String status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.groupCode = groupCode;
        this.name = name;
        this.city = city;
        this.eventDate = eventDate;
        this.maxBudget = maxBudget;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public Integer getMaxBudget() {
        return maxBudget;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
