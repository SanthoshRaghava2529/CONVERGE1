package com.converge.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.converge.backend.dto.PreferenceRequest;
import com.converge.backend.dto.PreferenceResponse;
import com.converge.backend.entity.Group;
import com.converge.backend.entity.GroupMember;
import com.converge.backend.entity.Preference;
import com.converge.backend.entity.User;
import com.converge.backend.repository.GroupMemberRepository;
import com.converge.backend.repository.GroupRepository;
import com.converge.backend.repository.PreferenceRepository;
import com.converge.backend.repository.UserRepository;

@Service
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    public PreferenceService(
            PreferenceRepository preferenceRepository,
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            UserRepository userRepository
    ) {
        this.preferenceRepository = preferenceRepository;
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PreferenceResponse savePreference(
            String email,
            String groupCode,
            PreferenceRequest request
    ) {

        User user = userRepository
                .findByEmail(email.trim().toLowerCase())
                .filter(User::isEnabled)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        Group group = groupRepository
                .findByGroupCode(
                        groupCode.trim().toUpperCase()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("Group not found")
                );

        GroupMember member =
                groupMemberRepository
                        .findByGroupIdAndUserId(
                                group.getId(),
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "You are not a member of this group"
                                )
                        );

        Preference preference =
                preferenceRepository
                        .findByGroupMemberId(member.getId())
                        .orElseGet(Preference::new);

        preference.setGroupMember(member);
        preference.setCategory(
                request.getCategory().trim()
        );
        preference.setGenre(
                request.getGenre().trim()
        );
        preference.setLanguage(
                request.getLanguage().trim()
        );
        preference.setMaxBudget(
                request.getMaxBudget()
        );
        preference.setMaxDurationMinutes(
                request.getMaxDurationMinutes()
        );
        preference.setPreferredStartTime(
                request.getPreferredStartTime()
        );
        preference.setMaxDistanceKm(
                request.getMaxDistanceKm()
        );

        Preference savedPreference =
                preferenceRepository.save(preference);

        return toResponse(savedPreference);
    }

    @Transactional(readOnly = true)
    public PreferenceResponse getMyPreference(
            String email,
            String groupCode
    ) {

        User user = userRepository
                .findByEmail(email.trim().toLowerCase())
                .filter(User::isEnabled)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        Group group = groupRepository
                .findByGroupCode(
                        groupCode.trim().toUpperCase()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("Group not found")
                );

        GroupMember member =
                groupMemberRepository
                        .findByGroupIdAndUserId(
                                group.getId(),
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "You are not a member of this group"
                                )
                        );

        Preference preference =
                preferenceRepository
                        .findByGroupMemberId(member.getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Preferences not submitted yet"
                                )
                        );

        return toResponse(preference);
    }

    private PreferenceResponse toResponse(
            Preference preference
    ) {

        return new PreferenceResponse(
                preference.getId(),
                preference.getGroupMember().getId(),
                preference.getCategory(),
                preference.getGenre(),
                preference.getLanguage(),
                preference.getMaxBudget(),
                preference.getMaxDurationMinutes(),
                preference.getPreferredStartTime(),
                preference.getMaxDistanceKm()
        );
    }
}
