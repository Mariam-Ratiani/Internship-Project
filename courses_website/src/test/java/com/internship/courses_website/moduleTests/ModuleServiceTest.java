package com.internship.courses_website.moduleTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.internship.courses_website.courses.entity.Course;
import com.internship.courses_website.module.Module;
import com.internship.courses_website.module.ModuleRepository;
import com.internship.courses_website.module.ModuleService;

class ModuleServiceTest {

    private ModuleService moduleService;

    @Mock
    private ModuleRepository moduleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        moduleService = new ModuleService(moduleRepository);
    }

    @Test
    void testGetModuleById_Success() {
        // Arrange
        Course mockCourse = new Course();
        mockCourse.setCourseId(1L);

        Module mockModule = new Module(mockCourse, "Introduction", "Getting started with Java", 1);
        mockModule.setId(1L);

        when(moduleRepository.findByCourse_CourseIdAndId(1L, 1L)).thenReturn(Optional.of(mockModule));

        // Act
        Module retrievedModule = moduleService.getModuleById(1L, 1L);

        // Assert
        assertThat(retrievedModule).isNotNull();
        assertThat(retrievedModule.getName()).isEqualTo("Introduction");
        verify(moduleRepository, times(1)).findByCourse_CourseIdAndId(1L, 1L);
    }

    @Test
    void testGetModuleById_NotFound() {
        // Arrange
        when(moduleRepository.findByCourse_CourseIdAndId(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> moduleService.getModuleById(1L, 1L));
        assertThat(exception.getMessage()).isEqualTo("Module not found by its id");
        verify(moduleRepository, times(1)).findByCourse_CourseIdAndId(1L, 1L);
    }

    @Test
    void testGetModulesByCourse() {
        // Arrange
        Course mockCourse = new Course();
        mockCourse.setCourseId(1L);

        Module module1 = new Module(mockCourse, "Introduction", "Getting started with Java", 1);
        Module module2 = new Module(mockCourse, "Advanced Topics", "Java advanced topics", 2);

        when(moduleRepository.findByCourse_CourseId(1L)).thenReturn(List.of(module1, module2));

        // Act
        List<Module> modules = moduleService.getModulesByCourse(1L);

        // Assert
        assertThat(modules).hasSize(2);
        assertThat(modules)
                .extracting(Module::getName)
                .containsExactlyInAnyOrder("Introduction", "Advanced Topics");
        verify(moduleRepository, times(1)).findByCourse_CourseId(1L);
    }

    @Test
    void testGetModuleByCourseAndPosition_Success() {
        // Arrange
        Course mockCourse = new Course();
        mockCourse.setCourseId(1L);

        Module mockModule = new Module(mockCourse, "Introduction", "Getting started with Java", 1);
        mockModule.setPosition(1);

        when(moduleRepository.findByCourse_CourseIdAndPosition(1L, 1)).thenReturn(Optional.of(mockModule));

        // Act
        Module retrievedModule = moduleService.getModuleByCourseAndPosition(1L, 1);

        // Assert
        assertThat(retrievedModule).isNotNull();
        assertThat(retrievedModule.getName()).isEqualTo("Introduction");
        verify(moduleRepository, times(1)).findByCourse_CourseIdAndPosition(1L, 1);
    }

    @Test
    void testGetModuleByCourseAndPosition_NotFound() {
        // Arrange
        when(moduleRepository.findByCourse_CourseIdAndPosition(1L, 1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> moduleService.getModuleByCourseAndPosition(1L, 1));
        assertThat(exception.getMessage()).isEqualTo("Module not found by its position");
        verify(moduleRepository, times(1)).findByCourse_CourseIdAndPosition(1L, 1);
    }
}
