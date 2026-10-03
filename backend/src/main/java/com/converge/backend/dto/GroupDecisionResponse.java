package com.converge.backend.dto;

import java.time.LocalDateTime;

public class GroupDecisionResponse {

    private Long id;
    private Long groupId;
    private String groupCode;
    private Long experienceId;
    private String experienceTitle;
    private Long scheduleId;
    private Long decidedByUserId;
    private String decidedByUserName;
    private LocalDateTime decidedAt;

    public GroupDecisionResponse(
            Long id,
            Long groupId,
            String groupCode,
            Long experienceId,
            String experienceTitle,
            Long scheduleId,
            Long decidedByUserId,
            String decidedByUserName,
            LocalDateTime decidedAt
    ) {
        this.id = id;
        this.groupId = groupId;
        this.groupCode = groupCode;
        this.experienceId = experienceId;
        this.experienceTitle = experienceTitle;
        this.scheduleId = scheduleId;
        this.decidedByUserId = decidedByUserId;
        this.decidedByUserName = decidedByUserName;
        this.decidedAt = decidedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public String getExperienceTitle() {
        return experienceTitle;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public Long getDecidedByUserId() {
        return decidedByUserId;
    }

    public String getDecidedByUserName() {
        return decidedByUserName;
    }

    public LocalDateTime getDecidedAt() {
        return decidedAt;
    }
}