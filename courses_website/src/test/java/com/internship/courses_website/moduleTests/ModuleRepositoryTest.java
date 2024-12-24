package com.internship.courses_website.moduleTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.categories.repository.CategoryRepository;
import com.internship.courses_website.courses.entity.Course;
import com.internship.courses_website.courses.repository.CoursesRepository;
import com.internship.courses_website.module.Module;
import com.internship.courses_website.module.ModuleRepository;

@DataJpaTest
public class ModuleRepositoryTest {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testFindByCourse_CourseId() {
        // Arrange
        Category programming = new Category("Programming", "Courses related to programming");
        categoryRepository.save(programming);

        Course course1 = new Course("Java Basics", "Learn Java programming", programming);
        Course course2 = new Course("Python Basics", "Learn Python programming", programming);
        coursesRepository.saveAll(List.of(course1, course2));

        Module module1 = new Module(course1, "Introduction", "Getting started with Java", 1);
        Module module2 = new Module(course1, "Advanced Topics", "Java advanced topics", 2);
        Module module3 = new Module(course2, "Introduction", "Getting started with Python", 3);
        moduleRepository.saveAll(List.of(module1, module2, module3));

        // Act
        List<Module> javaModules = moduleRepository.findByCourse_CourseId(course1.getCourseId());

        // Assert
        assertThat(javaModules).hasSize(2);
        assertThat(javaModules)
                .extracting(Module::getName)
                .containsExactlyInAnyOrder("Introduction", "Advanced Topics");
    }

    @Test
    void testFindByCourse_CourseIdAndId() {
        // Arrange
        Category programming = new Category("Programming", "Courses related to programming");
        categoryRepository.save(programming);

        Course course = new Course("Java Basics", "Learn Java programming", programming);
        coursesRepository.save(course);

        Module module = new Module(course, "Introduction", "Getting started with Java", 1);
        moduleRepository.save(module);

        // Act
        Optional<Module> retrievedModule = moduleRepository.findByCourse_CourseIdAndId(course.getCourseId(),
                module.getId());

        // Assert
        assertThat(retrievedModule).isPresent();
        assertThat(retrievedModule.get().getName()).isEqualTo("Introduction");
    }

    @Test
    void testFindByCourse_CourseIdAndPosition() {
        // Arrange
        Category programming = new Category("Programming", "Courses related to programming");
        categoryRepository.save(programming);

        Course course = new Course("Java Basics", "Learn Java programming", programming);
        coursesRepository.save(course);

        Module module1 = new Module(course, "Introduction", "Getting started with Java", 1);
        module1.setPosition(1);
        Module module2 = new Module(course, "Advanced Topics", "Java advanced topics", 2);
        module2.setPosition(2);
        moduleRepository.saveAll(List.of(module1, module2));

        // Act
        Optional<Module> retrievedModule = moduleRepository.findByCourse_CourseIdAndPosition(course.getCourseId(), 2);

        // Assert
        assertThat(retrievedModule).isPresent();
        assertThat(retrievedModule.get().getName()).isEqualTo("Advanced Topics");
    }

    @Test
    void testSaveModule() {
        // Arrange
        Category programming = new Category("Programming", "Courses related to programming");
        categoryRepository.save(programming);

        Course course = new Course("Java Basics", "Learn Java programming", programming);
        coursesRepository.save(course);

        Module module = new Module(course, "Introduction", "Getting started with Java", 1);

        // Act
        Module savedModule = moduleRepository.save(module);

        // Assert
        assertThat(savedModule.getId()).isNotNull();
        assertThat(savedModule.getName()).isEqualTo("Introduction");
        assertThat(savedModule.getCourse().getName()).isEqualTo("Java Basics");
    }
}
