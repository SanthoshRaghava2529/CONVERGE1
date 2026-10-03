package com.converge.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class GroupRoomResponse {

    private Long id;
    private String groupCode;
    private String name;
    private String city;
    private LocalDate eventDate;
    private Integer maxBudget;
    private String status;

    private Long creatorId;
    private String creatorName;

    private List<GroupRoomMemberResponse> members;

    public GroupRoomResponse() {
    }

    public GroupRoomResponse(
            Long id,
            String groupCode,
            String name,
            String city,
            LocalDate eventDate,
            Integer maxBudget,
            String status,
            Long creatorId,
            String creatorName,
            List<GroupRoomMemberResponse> members
    ) {
        this.id = id;
        this.groupCode = groupCode;
        this.name = name;
        this.city = city;
        this.eventDate = eventDate;
        this.maxBudget = maxBudget;
        this.status = status;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.members = members;
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

    public String getStatus() {
        return status;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public List<GroupRoomMemberResponse> getMembers() {
        return members;
    }
}
