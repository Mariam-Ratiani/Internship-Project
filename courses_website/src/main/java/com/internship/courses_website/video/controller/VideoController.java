package com.internship.courses_website.video.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.courses_website.video.entity.Video;
import com.internship.courses_website.video.service.VideoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/courses/{courseId}/modules/{moduleId}/videos")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public List<Video> getAllVideos(@PathVariable Long courseId, @PathVariable Long moduleId) {
        return videoService.getAllVideos(courseId, moduleId);
    }

    @PostMapping
    public Video addNewVideo(@PathVariable Long courseId, @PathVariable Long moduleId, @RequestBody Video video) {
        return videoService.addNewVideo(courseId, moduleId, video);
    }

}
