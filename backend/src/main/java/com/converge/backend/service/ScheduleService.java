package com.converge.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.converge.backend.entity.Experience;
import com.converge.backend.entity.Schedule;
import com.converge.backend.repository.ExperienceRepository;
import com.converge.backend.repository.ScheduleRepository;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ExperienceRepository experienceRepository;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            ExperienceRepository experienceRepository
    ) {
        this.scheduleRepository = scheduleRepository;
        this.experienceRepository = experienceRepository;
    }

    public List<Schedule> getSchedulesForExperience(Long experienceId) {

        if (!experienceRepository.existsById(experienceId)) {
            throw new IllegalArgumentException(
                    "Experience not found"
            );
        }

        return scheduleRepository
                .findByExperienceIdAndActiveTrue(experienceId);
    }

    public List<Schedule> getSchedulesForExperienceOnDate(
            Long experienceId,
            LocalDate date
    ) {

        if (!experienceRepository.existsById(experienceId)) {
            throw new IllegalArgumentException(
                    "Experience not found"
            );
        }

        return scheduleRepository
                .findByExperienceIdAndScheduleDateAndActiveTrue(
                        experienceId,
                        date
                );
    }

    public Schedule getScheduleById(Long scheduleId) {

        return scheduleRepository
                .findById(scheduleId)
                .filter(Schedule::isActive)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Schedule not found"
                        )
                );
    }

    public Schedule createSchedule(
            Long experienceId,
            Schedule schedule
    ) {

        Experience experience = experienceRepository
                .findById(experienceId)
                .filter(Experience::isActive)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Experience not found"
                        )
                );

        if (schedule.getAvailableSeats() == null) {
            schedule.setAvailableSeats(
                    schedule.getTotalSeats()
            );
        }

        schedule.setExperience(experience);
        schedule.setActive(true);

        return scheduleRepository.save(schedule);
    }
}
