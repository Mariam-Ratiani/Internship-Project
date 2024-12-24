package com.internship.courses_website.coursesTests;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.categories.repository.CategoryRepository;
import com.internship.courses_website.courses.entity.Course;
import com.internship.courses_website.courses.repository.CoursesRepository;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CoursesRepositoryTest {
    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveCourse() {
        // Arrange
        Category programming = new Category("Programming", "Courses related to programming");
        categoryRepository.save(programming);

        Course course = new Course("Java Basics", "Learn Java", programming);

        // Act
        Course savedCourse = coursesRepository.save(course);

        // Assert
        assertThat(savedCourse.getCourseId()).isNotNull();
        assertThat(savedCourse.getName()).isEqualTo("Java Basics");
        assertThat(savedCourse.getCategory().getName()).isEqualTo("Programming");
    }

    @Test
    void testFindByCategory() {
        // Arrange
        Category programming = new Category("Programming", "Courses related to programming");
        Category art = new Category("Art", "Courses related to art");
        categoryRepository.saveAll(List.of(programming, art));

        Course javaCourse = new Course("Java Basics", "Learn Java", programming);
        Course pythonCourse = new Course("Python Basics", "Learn Python", programming);
        Course paintingCourse = new Course("Painting", "Learn painting", art);

        coursesRepository.saveAll(List.of(javaCourse, pythonCourse, paintingCourse));

        // Act
        List<Course> programmingCourses = coursesRepository.findByCategory_CategoryId(programming.getCategoryId());

        // Assert
        assertThat(programmingCourses).hasSize(2);
        assertThat(programmingCourses)
                .extracting(Course::getName)
                .containsExactlyInAnyOrder("Java Basics", "Python Basics");
    }

    @Test
    void testDeleteCourse() {
        // Arrange
        Category programming = new Category("Programming", "Courses related to programming");
        categoryRepository.save(programming);

        Course course = new Course("Java Basics", "Learn Java", programming);
        coursesRepository.save(course);

        // Act
        coursesRepository.deleteById(course.getCourseId());

        // Assert
        assertThat(coursesRepository.findById(course.getCourseId())).isEmpty();
    }

}
