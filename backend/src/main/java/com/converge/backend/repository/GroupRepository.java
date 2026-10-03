package com.converge.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.converge.backend.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupCode(String groupCode);

    boolean existsByGroupCode(String groupCode);
}