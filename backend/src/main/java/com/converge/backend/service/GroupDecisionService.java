package com.converge.backend.service;

import com.converge.backend.dto.GroupDecisionRequest;
import com.converge.backend.dto.GroupDecisionResponse;
import com.converge.backend.entity.Group;
import com.converge.backend.entity.GroupDecision;
import com.converge.backend.entity.GroupMember;
import com.converge.backend.entity.Schedule;
import com.converge.backend.entity.User;
import com.converge.backend.repository.GroupDecisionRepository;
import com.converge.backend.repository.GroupMemberRepository;
import com.converge.backend.repository.GroupRepository;
import com.converge.backend.repository.ScheduleRepository;
import com.converge.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupDecisionService {

    private final GroupDecisionRepository groupDecisionRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public GroupDecisionService(
            GroupDecisionRepository groupDecisionRepository,
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            ScheduleRepository scheduleRepository,
            UserRepository userRepository
    ) {
        this.groupDecisionRepository = groupDecisionRepository;
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public GroupDecisionResponse makeDecision(
            String email,
            String groupCode,
            GroupDecisionRequest request
    ) {

        User user = findEnabledUser(email);

        Group group = findGroup(groupCode);

        ensureMember(
                group.getId(),
                user.getId()
        );

        if (groupDecisionRepository.existsByGroupId(group.getId())) {
            throw new IllegalArgumentException(
                    "A decision has already been made for this group"
            );
        }

        Schedule schedule = scheduleRepository
                .findById(request.getScheduleId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Schedule not found"
                        )
                );

        if (!schedule.isActive()) {
            throw new IllegalArgumentException(
                    "Selected schedule is inactive"
            );
        }

        if (schedule.getExperience() == null) {
            throw new IllegalArgumentException(
                    "Schedule has no associated experience"
            );
        }

        if (!schedule.getExperience()
                .getId()
                .equals(request.getExperienceId())) {

            throw new IllegalArgumentException(
                    "Selected schedule does not belong to the selected experience"
            );
        }

        if (!schedule.getScheduleDate()
                .equals(group.getEventDate())) {

            throw new IllegalArgumentException(
                    "Selected schedule is not on the group's event date"
            );
        }

        if (!schedule.getExperience()
                .getCity()
                .equalsIgnoreCase(group.getCity())) {

            throw new IllegalArgumentException(
                    "Selected experience is not available in the group's city"
            );
        }

        GroupDecision decision = new GroupDecision();

        decision.setGroup(group);
        decision.setExperienceId(
                schedule.getExperience().getId()
        );
        decision.setExperienceTitle(
                schedule.getExperience().getTitle()
        );
        decision.setScheduleId(schedule.getId());
        decision.setDecidedByUserId(user.getId());

        GroupDecision savedDecision =
                groupDecisionRepository.save(decision);

        group.setStatus(Group.GroupStatus.DECIDED);
        groupRepository.save(group);

        return toResponse(savedDecision);
    }

    @Transactional(readOnly = true)
    public GroupDecisionResponse getDecision(
            String email,
            String groupCode
    ) {

        User user = findEnabledUser(email);

        Group group = findGroup(groupCode);

        ensureMember(
                group.getId(),
                user.getId()
        );

        GroupDecision decision =
                groupDecisionRepository
                        .findByGroupId(group.getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "No decision has been made for this group"
                                )
                        );

        return toResponse(decision);
    }

    private User findEnabledUser(String email) {

        return userRepository
                .findByEmail(
                        email.trim().toLowerCase()
                )
                .filter(User::isEnabled)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );
    }

    private Group findGroup(String groupCode) {

        return groupRepository
                .findByGroupCode(
                        groupCode.trim().toUpperCase()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Group not found"
                        )
                );
    }

    private void ensureMember(
            Long groupId,
            Long userId
    ) {

        if (!groupMemberRepository
                .existsByGroupIdAndUserId(
                        groupId,
                        userId
                )) {

            throw new IllegalArgumentException(
                    "You are not a member of this group"
            );
        }
    }

    private GroupDecisionResponse toResponse(
            GroupDecision decision
    ) {

        Group group = decision.getGroup();

        User decidedBy =
                userRepository
                        .findById(
                                decision.getDecidedByUserId()
                        )
                        .orElse(null);

        String decidedByUserName =
                decidedBy != null
                        ? decidedBy.getFullName()
                        : "Unknown User";

        return new GroupDecisionResponse(
                decision.getId(),
                group.getId(),
                group.getGroupCode(),
                decision.getExperienceId(),
                decision.getExperienceTitle(),
                decision.getScheduleId(),
                decision.getDecidedByUserId(),
                decidedByUserName,
                decision.getDecidedAt()
        );
    }
}