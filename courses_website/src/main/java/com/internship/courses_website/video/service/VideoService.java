package com.internship.courses_website.video.service;

import java.util.List;
import com.internship.courses_website.module.Module;
import com.internship.courses_website.module.ModuleService;
import com.internship.courses_website.video.entity.Video;
import com.internship.courses_website.video.repository.VideoRepository;

public class VideoService {

    private final VideoRepository videoRepository;
    private final ModuleService moduleService;

    public VideoService(VideoRepository videoRepository, ModuleService moduleService) {
        this.videoRepository = videoRepository;
        this.moduleService = moduleService;
    }

    public List<Video> getAllVideos(Long courseId, Long moduleId) {
        // this function will throw a runtime exception if the module is not found
        moduleService.getModuleById(courseId, moduleId);

        // Fetch all videos for the module
        return videoRepository.findByModule_Id(moduleId);
    }

    public Video addNewVideo(Long courseId, Long moduleId, Video video) {
        Module module = moduleService.getModuleById(courseId, moduleId);
        video.setModule(module);
        return videoRepository.save(video);
    }

}
