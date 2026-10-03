package com.converge.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.converge.backend.entity.Preference;

public interface PreferenceRepository
        extends JpaRepository<Preference, Long> {

    Optional<Preference> findByGroupMemberId(
            Long groupMemberId
    );

    boolean existsByGroupMemberId(
            Long groupMemberId
    );
}
