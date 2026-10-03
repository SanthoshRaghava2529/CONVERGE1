package com.converge.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class JoinGroupRequest {

    @NotBlank(message = "Group code is required")
    @Size(min = 8, max = 20, message = "Invalid group code")
    private String groupCode;

    public JoinGroupRequest() {
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}