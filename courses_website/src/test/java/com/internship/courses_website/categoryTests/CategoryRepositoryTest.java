package com.internship.courses_website.categoryTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.internship.courses_website.CoursesWebsiteApplication;
import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.categories.repository.CategoryRepository;

@DataJpaTest
@ContextConfiguration(classes = CoursesWebsiteApplication.class)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSaveAndFindById() {
        // Arrange
        Category category = new Category("Programming", "Courses related to programming");

        // Act
        Category savedCategory = categoryRepository.save(category);
        Category foundCategory = categoryRepository.findById(savedCategory.getCategoryId()).orElse(null);

        // Assert
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo("Programming");
    }

    @Test
    public void testFindAll() {
        // Arrange
        categoryRepository.save(new Category("Art", "Courses related to art"));
        categoryRepository.save(new Category("History", "Courses related to history"));

        // Act
        List<Category> categories = categoryRepository.findAll();

        // Assert
        assertThat(categories).hasSize(2);
    }

}
