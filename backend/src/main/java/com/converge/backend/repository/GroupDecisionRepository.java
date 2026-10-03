package com.converge.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.converge.backend.entity.GroupDecision;

public interface GroupDecisionRepository
        extends JpaRepository<GroupDecision, Long> {

    Optional<GroupDecision> findByGroupId(Long groupId);

    boolean existsByGroupId(Long groupId);
}
