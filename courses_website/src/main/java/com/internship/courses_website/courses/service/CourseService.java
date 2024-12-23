package com.internship.courses_website.courses.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.internship.courses_website.courses.entity.Course;
import com.internship.courses_website.courses.repository.CoursesRepository;

@Service
public class CourseService {

    private final CoursesRepository coursesRepository;

    public CourseService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public List<Course> getAllCourses() {
        return coursesRepository.findAll();
    }

    public Course createCourse(Course course) {
        return coursesRepository.save(course);
    }

    public Course getCourseById(Long courseId) {
        return coursesRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course updateCourse(Course course) {
        return coursesRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        coursesRepository.deleteById(courseId);
    }

    public List<Course> getCoursesByCategory(Long categoryId) {
        return coursesRepository.findByCategory_CategoryId(categoryId);
    }

}
