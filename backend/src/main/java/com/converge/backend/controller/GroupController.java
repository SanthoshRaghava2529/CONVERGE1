package com.converge.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.converge.backend.dto.CreateGroupRequest;
import com.converge.backend.dto.GroupMemberResponse;
import com.converge.backend.dto.GroupResponse;
import com.converge.backend.dto.GroupRoomResponse;
import com.converge.backend.dto.JoinGroupRequest;
import com.converge.backend.security.JwtService;
import com.converge.backend.service.GroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    private final JwtService jwtService;

    public GroupController(
            GroupService groupService,
            JwtService jwtService
    ) {
        this.groupService = groupService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getMyGroups(
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return ResponseEntity.ok(
                groupService.getMyGroups(email)
        );
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody CreateGroupRequest request
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        GroupResponse response =
                groupService.createGroup(
                        email,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/join")
    public ResponseEntity<GroupMemberResponse> joinGroup(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody JoinGroupRequest request
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        GroupMemberResponse response =
                groupService.joinGroup(
                        email,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{groupCode}/members")
    public ResponseEntity<List<GroupMemberResponse>> getMembers(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupCode
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return ResponseEntity.ok(
                groupService.getMembers(
                        email,
                        groupCode
                )
        );
    }

    @GetMapping("/{groupCode}/room")
    public ResponseEntity<GroupRoomResponse> getGroupRoom(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupCode
    ) {

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return ResponseEntity.ok(
                groupService.getGroupRoom(
                        email,
                        groupCode
                )
        );
    }
}