package com.internship.courses_website.video.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.courses_website.video.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByModule_Id(Long moduleId);
}
