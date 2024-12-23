package com.internship.courses_website.categoryTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.internship.courses_website.categories.controller.CategoryController;
import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.categories.service.CategoryService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService; // Use @MockBean to mock CategoryService

    @Test
    void testGetAllCategories() throws Exception {
        // Arrange
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(
                new Category("Programming", "Courses related to programming"),
                new Category("Art", "Courses related to art")));

        // Act & Assert
        mockMvc.perform(get("/api/categories/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Programming"));
    }

    @Test
    void testPostCategory() throws Exception {
        // Arrange
        Category newCategory = new Category("History", "Courses related to history");
        when(categoryService.createCategory(any(Category.class))).thenReturn(newCategory);

        // Act & Assert
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"History\",\"description\":\"Courses related to history\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("History"))
                .andExpect(jsonPath("$.description").value("Courses related to history"));
    }
}
