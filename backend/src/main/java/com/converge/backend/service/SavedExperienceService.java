package com.converge.backend.service;

import com.converge.backend.dto.SavedExperienceResponse;
import com.converge.backend.entity.Experience;
import com.converge.backend.entity.SavedExperience;
import com.converge.backend.entity.User;
import com.converge.backend.repository.ExperienceRepository;
import com.converge.backend.repository.SavedExperienceRepository;
import com.converge.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SavedExperienceService {

    private final SavedExperienceRepository savedExperienceRepository;
    private final UserRepository userRepository;
    private final ExperienceRepository experienceRepository;

    public SavedExperienceService(
            SavedExperienceRepository savedExperienceRepository,
            UserRepository userRepository,
            ExperienceRepository experienceRepository
    ) {
        this.savedExperienceRepository = savedExperienceRepository;
        this.userRepository = userRepository;
        this.experienceRepository = experienceRepository;
    }

    @Transactional
    public SavedExperienceResponse saveExperience(
            String email,
            Long experienceId
    ) {

        User user = userRepository
                .findByEmail(email.trim().toLowerCase())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        if (!user.isEnabled()) {
            throw new IllegalArgumentException(
                    "User account is disabled"
            );
        }

        Experience experience = experienceRepository
                .findByIdAndActiveTrue(experienceId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Experience not found"
                        )
                );

        if (savedExperienceRepository
                .existsByUserIdAndExperienceId(
                        user.getId(),
                        experienceId
                )) {

            throw new IllegalArgumentException(
                    "Experience is already saved"
            );
        }

        SavedExperience savedExperience =
                new SavedExperience();

        savedExperience.setUser(user);
        savedExperience.setExperience(experience);

        SavedExperience saved =
                savedExperienceRepository.save(
                        savedExperience
                );

        return toResponse(saved);
    }

    @Transactional
    public void unsaveExperience(
            String email,
            Long experienceId
    ) {

        User user = userRepository
                .findByEmail(email.trim().toLowerCase())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        if (!user.isEnabled()) {
            throw new IllegalArgumentException(
                    "User account is disabled"
            );
        }

        if (!savedExperienceRepository
                .existsByUserIdAndExperienceId(
                        user.getId(),
                        experienceId
                )) {

            throw new IllegalArgumentException(
                    "Experience is not saved"
            );
        }

        savedExperienceRepository
                .deleteByUserIdAndExperienceId(
                        user.getId(),
                        experienceId
                );
    }

    @Transactional(readOnly = true)
    public List<SavedExperienceResponse> getSavedExperiences(
            String email
    ) {

        User user = userRepository
                .findByEmail(email.trim().toLowerCase())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        if (!user.isEnabled()) {
            throw new IllegalArgumentException(
                    "User account is disabled"
            );
        }

        return savedExperienceRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean isSaved(
            String email,
            Long experienceId
    ) {

        User user = userRepository
                .findByEmail(email.trim().toLowerCase())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        if (!user.isEnabled()) {
            throw new IllegalArgumentException(
                    "User account is disabled"
            );
        }

        return savedExperienceRepository
                .existsByUserIdAndExperienceId(
                        user.getId(),
                        experienceId
                );
    }

    private SavedExperienceResponse toResponse(
            SavedExperience savedExperience
    ) {

        Experience experience =
                savedExperience.getExperience();

        return new SavedExperienceResponse(
                savedExperience.getId(),
                experience.getId(),
                experience.getTitle(),
                experience.getCategory(),
                experience.getGenre(),
                experience.getLanguage(),
                experience.getCity(),
                experience.getVenueName(),
                experience.getDurationMinutes(),
                experience.getStartingPrice(),
                experience.getDescription(),
                experience.getImageUrl(),
                savedExperience.getCreatedAt()
        );
    }
}
