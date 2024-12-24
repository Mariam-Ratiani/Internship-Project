package com.internship.courses_website.module;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    // Find all modules by courseId
    List<Module> findByCourse_CourseId(Long courseId);

    Optional<Module> findByCourse_CourseIdAndId(Long courseId, Long moduleId);

    // Find a module by courseId and position
    Optional<Module> findByCourse_CourseIdAndPosition(Long courseId, Integer position);
}
