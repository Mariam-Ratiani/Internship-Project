package com.internship.courses_website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internship.courses_website.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
