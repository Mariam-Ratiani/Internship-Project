package com.internship.courses_website.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.categories.repository.CategoryRepository;
import com.internship.courses_website.courses.entity.Course;
import com.internship.courses_website.courses.repository.CoursesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class CategoryInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CoursesRepository courseRepository;

    @Autowired
    public CategoryInitializer(CategoryRepository categoryRepository, CoursesRepository courseRepository) {
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/import.json");

        // Parse JSON to a list of Category objects
        List<Category> categories = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        // Save categories to the database
        categoryRepository.saveAll(categories);

        System.out.println("Template categories added successfully!");
        addCourses();
    }

    private void addCourses() {
        // Add courses to the database
        Course course1 = new Course("Java Programming", "Learn Java programming from scratch",
                categoryRepository.findById(1L).get());
        Course course2 = new Course("Python Programming", "Learn Python programming from scratch",
                categoryRepository.findById(1L).get());
        Course course3 = new Course("Web Development", "Learn web development from scratch",
                categoryRepository.findById(2L).get());

        // Save courses to the database
        courseRepository.saveAll(List.of(course1, course2, course3));
        System.out.println("Template courses added successfully!");

    }

}
