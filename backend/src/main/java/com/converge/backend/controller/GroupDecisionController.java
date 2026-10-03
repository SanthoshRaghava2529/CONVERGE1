package com.converge.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.converge.backend.dto.GroupDecisionRequest;
import com.converge.backend.dto.GroupDecisionResponse;
import com.converge.backend.security.JwtService;
import com.converge.backend.service.GroupDecisionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class GroupDecisionController {

    private final GroupDecisionService groupDecisionService;
    private final JwtService jwtService;

    public GroupDecisionController(
            GroupDecisionService groupDecisionService,
            JwtService jwtService
    ) {
        this.groupDecisionService = groupDecisionService;
        this.jwtService = jwtService;
    }

    @PostMapping("/{groupCode}/decision")
    public ResponseEntity<GroupDecisionResponse> makeDecision(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupCode,
            @Valid @RequestBody GroupDecisionRequest request
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return ResponseEntity.ok(
                groupDecisionService.makeDecision(
                        email,
                        groupCode,
                        request
                )
        );
    }

    @GetMapping("/{groupCode}/decision")
    public ResponseEntity<GroupDecisionResponse> getDecision(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupCode
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return ResponseEntity.ok(
                groupDecisionService.getDecision(
                        email,
                        groupCode
                )
        );
    }
}