package com.converge.backend.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.converge.backend.dto.MatchResultResponse;
import com.converge.backend.entity.Experience;
import com.converge.backend.entity.Group;
import com.converge.backend.entity.GroupMember;
import com.converge.backend.entity.Preference;
import com.converge.backend.entity.Schedule;
import com.converge.backend.repository.GroupMemberRepository;
import com.converge.backend.repository.GroupRepository;
import com.converge.backend.repository.PreferenceRepository;
import com.converge.backend.repository.ScheduleRepository;
import com.converge.backend.repository.UserRepository;

@Service
public class MatchingService {

    private static final double BUDGET_WEIGHT = 0.25;
    private static final double TIME_WEIGHT = 0.20;
    private static final double GENRE_WEIGHT = 0.15;
    private static final double CATEGORY_WEIGHT = 0.15;
    private static final double DURATION_WEIGHT = 0.10;
    private static final double DISTANCE_WEIGHT = 0.10;
    private static final double LANGUAGE_WEIGHT = 0.05;

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final PreferenceRepository preferenceRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public MatchingService(
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            PreferenceRepository preferenceRepository,
            ScheduleRepository scheduleRepository,
            UserRepository userRepository
    ) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.preferenceRepository = preferenceRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<MatchResultResponse> findMatches(
            String email,
            String groupCode
    ) {

        userRepository
                .findByEmail(email.trim().toLowerCase())
                .filter(user -> user.isEnabled())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        Group group = groupRepository
                .findByGroupCode(
                        groupCode.trim().toUpperCase()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Group not found"
                        )
                );

        boolean isMember =
                groupMemberRepository
                        .existsByGroupIdAndUserId(
                                group.getId(),
                                userRepository
                                        .findByEmail(
                                                email.trim().toLowerCase()
                                        )
                                        .orElseThrow()
                                        .getId()
                        );

        if (!isMember) {
            throw new IllegalArgumentException(
                    "You are not a member of this group"
            );
        }

        List<GroupMember> members =
                groupMemberRepository
                        .findByGroupIdOrderByJoinedAtAsc(
                                group.getId()
                        );

        if (members.isEmpty()) {
            throw new IllegalArgumentException(
                    "Group has no members"
            );
        }

        List<Preference> preferences =
                new ArrayList<>();

        for (GroupMember member : members) {

            Preference preference =
                    preferenceRepository
                            .findByGroupMemberId(member.getId())
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "All group members must submit preferences before matching"
                                    )
                            );

            preferences.add(preference);
        }

        List<Schedule> schedules =
                scheduleRepository
                        .findByScheduleDateAndActiveTrue(
                                group.getEventDate()
                        );

        List<MatchResultResponse> results =
                new ArrayList<>();

        for (Schedule schedule : schedules) {

            Experience experience =
                    schedule.getExperience();

            if (!experience.isActive()) {
                continue;
            }

            if (!experience.getCity()
                    .equalsIgnoreCase(group.getCity())) {
                continue;
            }

            MatchCalculation calculation =
                    calculateMatch(
                            experience,
                            schedule,
                            preferences
                    );

            results.add(
                    new MatchResultResponse(
                            experience.getId(),
                            experience.getTitle(),
                            experience.getCategory(),
                            experience.getGenre(),
                            experience.getLanguage(),
                            experience.getCity(),
                            experience.getVenueName(),
                            experience.getDurationMinutes(),
                            experience.getStartingPrice(),
                            schedule.getId(),
                            schedule.getScheduleDate(),
                            schedule.getStartTime(),
                            schedule.getEndTime(),
                            calculation.groupScore,
                            calculation.averageScore,
                            calculation.minimumScore,
                            calculation.reasons,
                            calculation.compromises
                    )
            );
        }

        results.sort(
                Comparator.comparing(
                        MatchResultResponse::getGroupScore
                ).reversed()
        );

        return results;
    }

    private MatchCalculation calculateMatch(
            Experience experience,
            Schedule schedule,
            List<Preference> preferences
    ) {

        List<Double> memberScores =
                new ArrayList<>();

        int budgetMatches = 0;
        int timeMatches = 0;
        int genreMatches = 0;
        int categoryMatches = 0;
        int durationMatches = 0;
        int distanceMatches = 0;
        int languageMatches = 0;

        for (Preference preference : preferences) {

            double budgetScore =
                    calculateBudgetScore(
                            experience.getStartingPrice(),
                            preference.getMaxBudget()
                    );

            double timeScore =
                    calculateTimeScore(
                            schedule.getStartTime(),
                            preference.getPreferredStartTime()
                    );

            double genreScore =
                    exactMatchScore(
                            experience.getGenre(),
                            preference.getGenre()
                    );

            double categoryScore =
                    exactMatchScore(
                            experience.getCategory(),
                            preference.getCategory()
                    );

            double durationScore =
                    calculateDurationScore(
                            experience.getDurationMinutes(),
                            preference.getMaxDurationMinutes()
                    );

            double distanceScore =
                    calculateDistanceScore(
                            preference.getMaxDistanceKm()
                    );

            double languageScore =
                    exactMatchScore(
                            experience.getLanguage(),
                            preference.getLanguage()
                    );

            double memberScore =
                    budgetScore * BUDGET_WEIGHT
                    + timeScore * TIME_WEIGHT
                    + genreScore * GENRE_WEIGHT
                    + categoryScore * CATEGORY_WEIGHT
                    + durationScore * DURATION_WEIGHT
                    + distanceScore * DISTANCE_WEIGHT
                    + languageScore * LANGUAGE_WEIGHT;

            memberScores.add(memberScore);

            if (budgetScore >= 100) {
                budgetMatches++;
            }

            if (timeScore >= 80) {
                timeMatches++;
            }

            if (genreScore >= 100) {
                genreMatches++;
            }

            if (categoryScore >= 100) {
                categoryMatches++;
            }

            if (durationScore >= 100) {
                durationMatches++;
            }

            if (distanceScore >= 100) {
                distanceMatches++;
            }

            if (languageScore >= 100) {
                languageMatches++;
            }
        }

        double averageScore =
                memberScores.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0);

        double minimumScore =
                memberScores.stream()
                        .mapToDouble(Double::doubleValue)
                        .min()
                        .orElse(0);

        double groupScore =
                (averageScore * 0.80)
                + (minimumScore * 0.20);

        List<String> reasons =
                new ArrayList<>();

        List<String> compromises =
                new ArrayList<>();

        int memberCount = preferences.size();

        if (budgetMatches == memberCount) {
            reasons.add(
                    "Within everyone's budget"
            );
        } else if (budgetMatches > 0) {
            compromises.add(
                    "Budget works for "
                    + budgetMatches
                    + " of "
                    + memberCount
                    + " members"
            );
        }

        if (timeMatches == memberCount) {
            reasons.add(
                    "Timing works well for everyone"
            );
        } else if (timeMatches > 0) {
            compromises.add(
                    "Timing works well for "
                    + timeMatches
                    + " of "
                    + memberCount
                    + " members"
            );
        }

        if (genreMatches == memberCount) {
            reasons.add(
                    "Genre matches everyone's preference"
            );
        } else if (genreMatches > 0) {
            compromises.add(
                    "Genre matches "
                    + genreMatches
                    + " of "
                    + memberCount
                    + " members"
            );
        }

        if (categoryMatches == memberCount) {
            reasons.add(
                    "Category matches everyone"
            );
        }

        if (durationMatches == memberCount) {
            reasons.add(
                    "Duration fits everyone's limit"
            );
        }

        if (languageMatches == memberCount) {
            reasons.add(
                    "Language matches everyone"
            );
        }

        if (distanceMatches == memberCount) {
            reasons.add(
                    "Distance works for everyone"
            );
        }

        if (reasons.isEmpty()) {
            reasons.add(
                    "This option provides the strongest overall group compatibility"
            );
        }

        if (compromises.isEmpty()) {
            compromises.add(
                    "No major compromises detected"
            );
        }

        return new MatchCalculation(
                groupScore,
                averageScore,
                minimumScore,
                reasons,
                compromises
        );
    }

    private double calculateBudgetScore(
            BigDecimal price,
            Integer maxBudget
    ) {

        double actualPrice =
                price.doubleValue();

        double budget =
                maxBudget.doubleValue();

        if (actualPrice <= budget) {
            return 100;
        }

        double excess =
                (actualPrice - budget) / budget;

        return Math.max(
                0,
                100 - (excess * 100)
        );
    }

    private double calculateTimeScore(
            LocalTime actualTime,
            LocalTime preferredTime
    ) {

        long minutes =
                Math.abs(
                        Duration.between(
                                preferredTime,
                                actualTime
                        ).toMinutes()
                );

        if (minutes <= 30) {
            return 100;
        }

        if (minutes <= 60) {
            return 85;
        }

        if (minutes <= 120) {
            return 60;
        }

        if (minutes <= 180) {
            return 30;
        }

        return 0;
    }

    private double calculateDurationScore(
            Integer actualDuration,
            Integer maxDuration
    ) {

        if (actualDuration <= maxDuration) {
            return 100;
        }

        double excess =
                (double) (actualDuration - maxDuration)
                / maxDuration;

        return Math.max(
                0,
                100 - (excess * 100)
        );
    }

    private double calculateDistanceScore(
            Integer maxDistanceKm
    ) {

        /*
         * Current seed data does not contain geographic
         * coordinates, so we cannot calculate a real
         * venue distance yet.
         *
         * For now, group-city filtering guarantees the
         * experience is inside the requested city.
         */
        return 100;
    }

    private double exactMatchScore(
            String actual,
            String preferred
    ) {

        if (actual == null || preferred == null) {
            return 0;
        }

        return actual.equalsIgnoreCase(
                preferred.trim()
        ) ? 100 : 0;
    }

    private static class MatchCalculation {

        private final double groupScore;
        private final double averageScore;
        private final double minimumScore;
        private final List<String> reasons;
        private final List<String> compromises;

        private MatchCalculation(
                double groupScore,
                double averageScore,
                double minimumScore,
                List<String> reasons,
                List<String> compromises
        ) {
            this.groupScore = groupScore;
            this.averageScore = averageScore;
            this.minimumScore = minimumScore;
            this.reasons = reasons;
            this.compromises = compromises;
        }
    }
}