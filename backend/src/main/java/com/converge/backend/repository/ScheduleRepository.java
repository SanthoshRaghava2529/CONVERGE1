package com.converge.backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.converge.backend.entity.Schedule;

import jakarta.persistence.LockModeType;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByExperienceIdAndScheduleDateAndActiveTrue(
            Long experienceId,
            LocalDate scheduleDate
    );

    List<Schedule> findByExperienceIdAndActiveTrue(
            Long experienceId
    );

    List<Schedule> findByScheduleDateAndActiveTrue(
            LocalDate scheduleDate
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT s
            FROM Schedule s
            WHERE s.id = :scheduleId
            AND s.active = true
            """)
    Optional<Schedule> findActiveScheduleForUpdate(
            @Param("scheduleId") Long scheduleId
    );
}