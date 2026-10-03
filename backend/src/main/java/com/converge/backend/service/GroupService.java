package com.converge.backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.converge.backend.dto.CreateGroupRequest;
import com.converge.backend.dto.GroupMemberResponse;
import com.converge.backend.dto.GroupResponse;
import com.converge.backend.dto.GroupRoomMemberResponse;
import com.converge.backend.dto.GroupRoomResponse;
import com.converge.backend.dto.JoinGroupRequest;
import com.converge.backend.entity.Group;
import com.converge.backend.entity.GroupMember;
import com.converge.backend.entity.User;
import com.converge.backend.repository.GroupMemberRepository;
import com.converge.backend.repository.GroupRepository;
import com.converge.backend.repository.PreferenceRepository;
import com.converge.backend.repository.UserRepository;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final PreferenceRepository preferenceRepository;

    public GroupService(
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            UserRepository userRepository,
            PreferenceRepository preferenceRepository
    ) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.userRepository = userRepository;
        this.preferenceRepository = preferenceRepository;
    }

    @Transactional
    public GroupResponse createGroup(
            String email,
            CreateGroupRequest request
    ) {

        User user = findEnabledUser(email);

        Group group = new Group();

        group.setGroupCode(generateUniqueGroupCode());
        group.setName(request.getName().trim());
        group.setCity(request.getCity().trim());
        group.setEventDate(request.getEventDate());
        group.setMaxBudget(request.getMaxBudget());
        group.setCreator(user);
        group.setStatus(Group.GroupStatus.OPEN);

        Group savedGroup = groupRepository.save(group);

        GroupMember owner = new GroupMember();

        owner.setGroup(savedGroup);
        owner.setUser(user);
        owner.setRole(GroupMember.MemberRole.OWNER);

        groupMemberRepository.save(owner);

        return toGroupResponse(savedGroup);
    }

    @Transactional
    public GroupMemberResponse joinGroup(
            String email,
            JoinGroupRequest request
    ) {

        User user = findEnabledUser(email);

        String groupCode =
                request.getGroupCode()
                        .trim()
                        .toUpperCase();

        Group group = groupRepository
                .findByGroupCode(groupCode)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Group not found"
                        )
                );

        if (group.getStatus() != Group.GroupStatus.OPEN) {
            throw new IllegalArgumentException(
                    "This group is no longer accepting members"
            );
        }

        if (groupMemberRepository.existsByGroupIdAndUserId(
                group.getId(),
                user.getId()
        )) {
            throw new IllegalArgumentException(
                    "User is already a member of this group"
            );
        }

        GroupMember member = new GroupMember();

        member.setGroup(group);
        member.setUser(user);
        member.setRole(GroupMember.MemberRole.MEMBER);

        GroupMember savedMember =
                groupMemberRepository.save(member);

        return new GroupMemberResponse(
                savedMember.getUser().getId(),
                savedMember.getUser().getFullName(),
                savedMember.getUser().getEmail(),
                savedMember.getRole().name(),
                savedMember.getJoinedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> getMyGroups(String email) {

        User user = findEnabledUser(email);

        return groupMemberRepository
                .findByUserIdOrderByJoinedAtDesc(user.getId())
                .stream()
                .map(member -> toGroupResponse(member.getGroup()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GroupMemberResponse> getMembers(
            String email,
            String groupCode
    ) {

        User user = findEnabledUser(email);

        Group group = findGroup(groupCode);

        ensureMember(
                group.getId(),
                user.getId()
        );

        return groupMemberRepository
                .findByGroupIdOrderByJoinedAtAsc(
                        group.getId()
                )
                .stream()
                .map(member ->
                        new GroupMemberResponse(
                                member.getUser().getId(),
                                member.getUser().getFullName(),
                                member.getUser().getEmail(),
                                member.getRole().name(),
                                member.getJoinedAt()
                        )
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public GroupRoomResponse getGroupRoom(
            String email,
            String groupCode
    ) {

        User user = findEnabledUser(email);

        Group group = findGroup(groupCode);

        ensureMember(
                group.getId(),
                user.getId()
        );

        List<GroupMember> members =
                groupMemberRepository
                        .findByGroupIdOrderByJoinedAtAsc(
                                group.getId()
                        );

        List<GroupRoomMemberResponse> memberResponses =
                members.stream()
                        .map(member ->
                                new GroupRoomMemberResponse(
                                        member.getUser().getId(),
                                        member.getUser().getFullName(),
                                        member.getRole().name(),
                                        preferenceRepository
                                                .existsByGroupMemberId(
                                                        member.getId()
                                                )
                                )
                        )
                        .toList();

        User creator = group.getCreator();

        return new GroupRoomResponse(
                group.getId(),
                group.getGroupCode(),
                group.getName(),
                group.getCity(),
                group.getEventDate(),
                group.getMaxBudget(),
                group.getStatus().name(),
                creator.getId(),
                creator.getFullName(),
                memberResponses
        );
    }

    private User findEnabledUser(String email) {

        User user = userRepository
                .findByEmail(email.trim().toLowerCase())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );

        if (!user.isEnabled()) {
            throw new IllegalArgumentException(
                    "User account is disabled"
            );
        }

        return user;
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

        if (!groupMemberRepository.existsByGroupIdAndUserId(
                groupId,
                userId
        )) {
            throw new IllegalArgumentException(
                    "You are not a member of this group"
            );
        }
    }

    private GroupResponse toGroupResponse(
            Group group
    ) {

        User creator = group.getCreator();

        return new GroupResponse(
                group.getId(),
                group.getGroupCode(),
                group.getName(),
                group.getCity(),
                group.getEventDate(),
                group.getMaxBudget(),
                creator.getId(),
                creator.getFullName(),
                group.getStatus().name(),
                group.getCreatedAt()
        );
    }

    private String generateUniqueGroupCode() {

        String code;

        do {
            code =
                    "CNV-" +
                    UUID.randomUUID()
                            .toString()
                            .substring(0, 4)
                            .toUpperCase();

        } while (
                groupRepository.existsByGroupCode(code)
        );

        return code;
    }
}