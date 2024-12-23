package com.internship.courses_website.categories.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/*
 * !!! LATER ADD USING LOMBOK !!!
 */

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String description;

    // Constructors
    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and setters
    public Long getCategoryId() {
        return categoryId;
    }

    // The ID is generated once and should remain unchanged for the lifetime of the
    // entity
    // public void setCategoryId(Long categoryId) {
    // this.categoryId = categoryId;
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
