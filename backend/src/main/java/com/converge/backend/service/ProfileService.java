package com.converge.backend.service;

import com.converge.backend.dto.ProfileResponse;
import com.converge.backend.dto.ProfileUpdateRequest;
import com.converge.backend.entity.User;
import com.converge.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return mapToResponse(user);
    }

    @Transactional
    public ProfileResponse updateProfile(
            String email,
            ProfileUpdateRequest request
    ) {

        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String fullName = request.getFullName().trim();

        if (fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }

        user.setFullName(fullName);

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    private ProfileResponse mapToResponse(User user) {

        return new ProfileResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().name(),
                user.isEnabled(),
                user.getCreatedAt()
        );
    }
}
