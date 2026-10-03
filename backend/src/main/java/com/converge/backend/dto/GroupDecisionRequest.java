package com.converge.backend.dto;

import jakarta.validation.constraints.NotNull;

public class GroupDecisionRequest {

    @NotNull
    private Long experienceId;

    @NotNull
    private Long scheduleId;

    public GroupDecisionRequest() {
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
