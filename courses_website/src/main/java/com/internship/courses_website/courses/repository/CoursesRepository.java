package com.internship.courses_website.courses.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internship.courses_website.courses.entity.Course;

@Repository
public interface CoursesRepository extends JpaRepository<Course, Long> {
    List<Course> findByCategory_CategoryId(Long categoryId);
}
