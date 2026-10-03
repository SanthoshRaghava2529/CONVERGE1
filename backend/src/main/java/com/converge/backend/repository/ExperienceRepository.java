package com.converge.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.converge.backend.entity.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    List<Experience> findByActiveTrue();

    Optional<Experience> findByIdAndActiveTrue(Long id);

    List<Experience> findByCityIgnoreCaseAndActiveTrue(String city);

    List<Experience> findByCategoryIgnoreCaseAndActiveTrue(String category);

    List<Experience> findByCityIgnoreCaseAndCategoryIgnoreCaseAndActiveTrue(
            String city,
            String category
    );
}