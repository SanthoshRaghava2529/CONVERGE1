package com.converge.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.converge.backend.dto.MatchResultResponse;
import com.converge.backend.service.MatchingService;

@RestController
@RequestMapping("/api/groups")
public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(
            MatchingService matchingService
    ) {
        this.matchingService = matchingService;
    }

    @GetMapping("/{groupCode}/matches")
    public ResponseEntity<List<MatchResultResponse>> getMatches(
            @PathVariable String groupCode,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                matchingService.findMatches(
                        email,
                        groupCode
                )
        );
    }
}