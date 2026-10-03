package com.converge.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.converge.backend.entity.GroupMember;

public interface GroupMemberRepository
        extends JpaRepository<GroupMember, Long> {

    Optional<GroupMember> findByGroupIdAndUserId(
            Long groupId,
            Long userId
    );

    boolean existsByGroupIdAndUserId(
            Long groupId,
            Long userId
    );

    List<GroupMember> findByGroupIdOrderByJoinedAtAsc(
            Long groupId
    );

    List<GroupMember> findByUserIdOrderByJoinedAtDesc(
            Long userId
    );
}
