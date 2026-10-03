package com.converge.backend.dto;

public class GroupRoomMemberResponse {

    private Long userId;
    private String fullName;
    private String role;
    private boolean preferenceSubmitted;

    public GroupRoomMemberResponse() {
    }

    public GroupRoomMemberResponse(
            Long userId,
            String fullName,
            String role,
            boolean preferenceSubmitted
    ) {
        this.userId = userId;
        this.fullName = fullName;
        this.role = role;
        this.preferenceSubmitted = preferenceSubmitted;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public boolean isPreferenceSubmitted() {
        return preferenceSubmitted;
    }
}