package com.internship.courses_website.moduleTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.internship.courses_website.courses.entity.Course;
import com.internship.courses_website.module.Module;
import com.internship.courses_website.module.ModuleController;
import com.internship.courses_website.module.ModuleService;

@WebMvcTest(ModuleController.class)
public class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModuleService moduleService;

    private Course mockCourse;
    private Module mockModule1;
    private Module mockModule2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockCourse = new Course();
        mockCourse.setCourseId(1L);

        mockModule1 = new Module(mockCourse, "Introduction", "Getting started with Java", 1);
        mockModule1.setId(1L);
        mockModule1.setPosition(1);

        mockModule2 = new Module(mockCourse, "Advanced Topics", "Java advanced topics", 2);
        mockModule2.setId(2L);
        mockModule2.setPosition(2);
    }

    @Test
    void testGetModuleById() throws Exception {
        // Arrange
        when(moduleService.getModuleById(1L, 1L)).thenReturn(mockModule1);

        // Act & Assert
        mockMvc.perform(get("/api/1/modules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Introduction"))
                .andExpect(jsonPath("$.description").value("Getting started with Java"))
                .andExpect(jsonPath("$.course.courseId").value(1));
    }

    @Test
    void testGetModulesByCourse() throws Exception {
        // Arrange
        List<Module> modules = Arrays.asList(mockModule1, mockModule2);
        when(moduleService.getModulesByCourse(1L)).thenReturn(modules);

        // Act & Assert
        mockMvc.perform(get("/api/1/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Introduction"))
                .andExpect(jsonPath("$[1].name").value("Advanced Topics"));
    }

    @Test
    void testGetModuleByCourseAndPosition() throws Exception {
        // Arrange
        when(moduleService.getModuleByCourseAndPosition(1L, 2)).thenReturn(mockModule2);

        // Act & Assert
        mockMvc.perform(get("/api/1/modules/position/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Advanced Topics"))
                .andExpect(jsonPath("$.position").value(2))
                .andExpect(jsonPath("$.course.courseId").value(1));
    }
}
