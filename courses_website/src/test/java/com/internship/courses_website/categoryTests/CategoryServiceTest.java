package com.internship.courses_website.categoryTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.categories.repository.CategoryRepository;
import com.internship.courses_website.categories.service.CategoryService;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCategoryById() {
        // Arrange
        Category category = new Category("Programming", "Courses related to programming");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act
        Category result = categoryService.getCategoryById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Programming");
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        Category category1 = new Category("Programming", "Courses related to programming");
        Category category2 = new Category("Art", "Courses related to art");
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Act
        List<Category> result = categoryService.getAllCategories();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).contains(category1, category2);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testCreateCategory() {
        // Arrange
        Category category = new Category("Programming", "Courses related to programming");
        when(categoryRepository.save(category)).thenReturn(category);

        // Act
        Category result = categoryService.createCategory(category);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Programming");
        verify(categoryRepository, times(1)).save(category);
    }
}
