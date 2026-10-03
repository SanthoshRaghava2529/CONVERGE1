package com.converge.backend.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.converge.backend.entity.Schedule;
import com.converge.backend.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/experience/{experienceId}")
    public ResponseEntity<?> getSchedulesForExperience(
            @PathVariable Long experienceId,
            @RequestParam(required = false) LocalDate date
    ) {

        try {

            List<Schedule> schedules;

            if (date != null) {
                schedules = scheduleService
                        .getSchedulesForExperienceOnDate(
                                experienceId,
                                date
                        );
            } else {
                schedules = scheduleService
                        .getSchedulesForExperience(experienceId);
            }

            return ResponseEntity.ok(schedules);

        } catch (IllegalArgumentException exception) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message",
                            exception.getMessage()
                    ));
        }
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getScheduleById(
            @PathVariable Long scheduleId
    ) {

        try {

            return ResponseEntity.ok(
                    scheduleService.getScheduleById(scheduleId)
            );

        } catch (IllegalArgumentException exception) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message",
                            exception.getMessage()
                    ));
        }
    }
}
