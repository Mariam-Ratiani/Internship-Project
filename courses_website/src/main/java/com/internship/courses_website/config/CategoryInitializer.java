package com.internship.courses_website.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.categories.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class CategoryInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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

    }

}
