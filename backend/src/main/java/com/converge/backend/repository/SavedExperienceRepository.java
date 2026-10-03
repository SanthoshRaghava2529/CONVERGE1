package com.converge.backend.repository;

import com.converge.backend.entity.SavedExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedExperienceRepository
        extends JpaRepository<SavedExperience, Long> {

    Optional<SavedExperience> findByUserIdAndExperienceId(
            Long userId,
            Long experienceId
    );

    List<SavedExperience> findByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    boolean existsByUserIdAndExperienceId(
            Long userId,
            Long experienceId
    );

    void deleteByUserIdAndExperienceId(
            Long userId,
            Long experienceId
    );
}
