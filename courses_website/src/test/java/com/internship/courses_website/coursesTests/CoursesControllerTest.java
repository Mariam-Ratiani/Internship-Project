package com.internship.courses_website.coursesTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.courses.controller.CoursesController;
import com.internship.courses_website.courses.entity.Course;
import com.internship.courses_website.courses.service.CourseService;

@WebMvcTest(CoursesController.class)
public class CoursesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService; // Use @MockBean to mock coursesController

    @Test
    void testGetAllCourses() throws Exception {
        Category programming = new Category("Programming", "Programming courses");

        when(courseService.getAllCourses()).thenReturn(Arrays.asList(
                new Course("Java", "Java programming language", programming),
                new Course("Python", "Python programming language", programming)));

        mockMvc.perform(get("/api/courses/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Java"));
    }

    @Test
    void testGetCourseById() throws Exception {
        // Arrange
        Course course = new Course("Java Basics", "Learn Java", new Category("Programming", "Programming courses"));
        when(courseService.getCourseById(1L)).thenReturn(course);

        // Act & Assert
        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java Basics"))
                .andExpect(jsonPath("$.description").value("Learn Java"));

        verify(courseService, times(1)).getCourseById(1L);
    }

    @Test
    void testCreateCourse() throws Exception {
        // Arrange
        Category category = new Category("Programming", "Programming courses");
        Course course = new Course("Java Basics", "Learn Java", category);
        when(courseService.createCourse(any(Course.class))).thenReturn(course);

        // Act & Assert
        mockMvc.perform(post("/api/courses/create")
                .contentType("application/json")
                .content("""
                        {
                          "name": "Java Basics",
                          "description": "Learn Java",
                          "category": {
                            "categoryId": 1,
                            "name": "Programming",
                            "description": "Programming courses"
                          }
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java Basics"))
                .andExpect(jsonPath("$.category.name").value("Programming"));

        verify(courseService, times(1)).createCourse(any(Course.class));
    }

    @Test
    void testUpdateCourse() throws Exception {
        // Arrange
        Category category = new Category("Programming", "Programming courses");
        Course updatedCourse = new Course("Advanced Java", "Learn Advanced Java", category);
        when(courseService.updateCourse(any(Course.class))).thenReturn(updatedCourse);

        // Act & Assert
        mockMvc.perform(put("/api/courses/update")
                .contentType("application/json")
                .content("""
                        {
                          "name": "Advanced Java",
                          "description": "Learn Advanced Java",
                          "category": {
                            "categoryId": 1,
                            "name": "Programming",
                            "description": "Programming courses"
                          }
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Advanced Java"))
                .andExpect(jsonPath("$.description").value("Learn Advanced Java"))
                .andExpect(jsonPath("$.category.name").value("Programming"));

        verify(courseService, times(1)).updateCourse(any(Course.class));
    }

    @Test
    void testGetCoursesByCategory() throws Exception {
        // Arrange
        Category category = new Category("Programming", "Programming courses");
        when(courseService.getCoursesByCategory(1L)).thenReturn(List.of(
                new Course("Java Basics", "Learn Java", category),
                new Course("Python Basics", "Learn Python", category)));

        // Act & Assert
        mockMvc.perform(get("/api/courses/by-category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Java Basics"))
                .andExpect(jsonPath("$[1].name").value("Python Basics"));

        verify(courseService, times(1)).getCoursesByCategory(1L);
    }
}
