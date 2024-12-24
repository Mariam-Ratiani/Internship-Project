package com.internship.courses_website.coursesTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.courses.entity.Course;
import com.internship.courses_website.courses.repository.CoursesRepository;
import com.internship.courses_website.courses.service.CourseService;

public class CoursesServiceTest {

    @Mock
    private CoursesRepository coursesRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCourses() {
        // Arrange
        Course course1 = new Course("Java Basics", "Learn Java", null);
        Course course2 = new Course("Python Basics", "Learn Python", null);
        when(coursesRepository.findAll()).thenReturn(List.of(course1, course2));

        // Act
        List<Course> courses = courseService.getAllCourses();

        // Assert
        assertThat(courses).hasSize(2);
        assertThat(courses).extracting(Course::getName).contains("Java Basics", "Python Basics");
        verify(coursesRepository, times(1)).findAll();
    }

    @Test
    void testCreateCourse() {
        // Arrange
        Course course = new Course("Java Basics", "Learn Java", null);
        when(coursesRepository.save(course)).thenReturn(course);

        // Act
        Course createdCourse = courseService.createCourse(course);

        // Assert
        assertThat(createdCourse.getName()).isEqualTo("Java Basics");
        verify(coursesRepository, times(1)).save(course);
    }

    @Test
    void testGetCourseById() {
        // Arrange
        Course course = new Course("Java Basics", "Learn Java", null);
        when(coursesRepository.findById(1L)).thenReturn(Optional.of(course));

        // Act
        Course foundCourse = courseService.getCourseById(1L);

        // Assert
        assertThat(foundCourse.getName()).isEqualTo("Java Basics");
        verify(coursesRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCourseById_NotFound() {
        // Arrange
        when(coursesRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        try {
            courseService.getCourseById(1L);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Course not found");
        }
        verify(coursesRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateCourse() {
        // Arrange
        Course course = new Course("Java Basics", "Learn Java", null);
        when(coursesRepository.save(course)).thenReturn(course);

        // Act
        Course updatedCourse = courseService.updateCourse(course);

        // Assert
        assertThat(updatedCourse.getName()).isEqualTo("Java Basics");
        verify(coursesRepository, times(1)).save(course);
    }

    @Test
    void testDeleteCourse() {
        // Arrange
        Long courseId = 1L;

        // Act
        courseService.deleteCourse(courseId);

        // Assert
        verify(coursesRepository, times(1)).deleteById(courseId);
    }

    @Test
    void testGetCoursesByCategory() {
        // Arrange
        Category programming = new Category("Programming", "Courses related to programming");
        Course javaCourse = new Course("Java Basics", "Learn Java", programming);
        Course pythonCourse = new Course("Python Basics", "Learn Python", programming);
        when(coursesRepository.findByCategory_CategoryId(1L)).thenReturn(List.of(javaCourse, pythonCourse));

        // Act
        List<Course> courses = courseService.getCoursesByCategory(1L);

        // Assert
        assertThat(courses).hasSize(2);
        assertThat(courses).extracting(Course::getName).contains("Java Basics", "Python Basics");
        verify(coursesRepository, times(1)).findByCategory_CategoryId(1L);
    }

}
