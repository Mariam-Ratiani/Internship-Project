package com.internship.courses_website.videoTests;

import com.internship.courses_website.module.Module;
import com.internship.courses_website.module.ModuleService;
import com.internship.courses_website.video.entity.Video;
import com.internship.courses_website.video.repository.VideoRepository;
import com.internship.courses_website.video.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VideoServiceTests {

    private VideoRepository videoRepository;
    private ModuleService moduleService;
    private VideoService videoService;

    @BeforeEach
    void setup() {
        videoRepository = mock(VideoRepository.class);
        moduleService = mock(ModuleService.class);
        videoService = new VideoService(videoRepository, moduleService);
    }

    @Test
    void testGetAllVideos() {
        // Arrange
        Long courseId = 1L;
        Long moduleId = 1L;
        Module mockModule = new Module();
        mockModule.setId(moduleId);

        List<Video> mockVideos = Arrays.asList(
                new Video(mockModule, "https://example.com/video1", 120L, "Video 1"),
                new Video(mockModule, "https://example.com/video2", 150L, "Video 2"));

        when(moduleService.getModuleById(courseId, moduleId)).thenReturn(mockModule);
        when(videoRepository.findByModule_Id(moduleId)).thenReturn(mockVideos);

        // Act
        List<Video> videos = videoService.getAllVideos(courseId, moduleId);

        // Assert
        assertThat(videos).hasSize(2);
        assertThat(videos)
                .extracting(Video::getTitle)
                .containsExactlyInAnyOrder("Video 1", "Video 2");

        verify(moduleService, times(1)).getModuleById(courseId, moduleId);
        verify(videoRepository, times(1)).findByModule_Id(moduleId);
    }

    @Test
    void testGetAllVideos_ModuleNotFound() {
        // Arrange
        Long courseId = 1L;
        Long moduleId = 1L;

        when(moduleService.getModuleById(courseId, moduleId)).thenThrow(new RuntimeException("Module not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> videoService.getAllVideos(courseId, moduleId));
        assertThat(exception.getMessage()).isEqualTo("Module not found");

        verify(moduleService, times(1)).getModuleById(courseId, moduleId);
        verify(videoRepository, never()).findByModule_Id(anyLong());
    }

    @Test
    void testAddNewVideo() {
        // Arrange
        Long courseId = 1L;
        Long moduleId = 1L;
        Module mockModule = new Module();
        mockModule.setId(moduleId);

        Video newVideo = new Video(mockModule, "https://example.com/new-video", 200L, "New Video");
        Video savedVideo = new Video(mockModule, "https://example.com/new-video", 200L, "New Video");
        savedVideo.setId(1L);

        when(moduleService.getModuleById(courseId, moduleId)).thenReturn(mockModule);
        when(videoRepository.save(any(Video.class))).thenReturn(savedVideo);

        // Act
        Video result = videoService.addNewVideo(courseId, moduleId, newVideo);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("New Video");
        assertThat(result.getUrl()).isEqualTo("https://example.com/new-video");

        verify(moduleService, times(1)).getModuleById(courseId, moduleId);
        verify(videoRepository, times(1)).save(newVideo);
    }

    @Test
    void testAddNewVideo_ModuleNotFound() {
        // Arrange
        Long courseId = 1L;
        Long moduleId = 1L;
        Video newVideo = new Video();

        when(moduleService.getModuleById(courseId, moduleId)).thenThrow(new RuntimeException("Module not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> videoService.addNewVideo(courseId, moduleId, newVideo));
        assertThat(exception.getMessage()).isEqualTo("Module not found");

        verify(moduleService, times(1)).getModuleById(courseId, moduleId);
        verify(videoRepository, never()).save(any(Video.class));
    }
}
