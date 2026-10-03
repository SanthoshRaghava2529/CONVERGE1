package com.converge.backend.dto;

import java.time.LocalDateTime;

public class GroupMemberResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String role;
    private LocalDateTime joinedAt;

    public GroupMemberResponse() {
    }

    public GroupMemberResponse(
            Long userId,
            String fullName,
            String email,
            String role,
            LocalDateTime joinedAt
    ) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
}
