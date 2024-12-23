package com.internship.courses_website.categories.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.categories.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); // Calls the repository
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category); // Saves the category to the database
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
