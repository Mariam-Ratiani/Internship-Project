package com.internship.courses_website.videoTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.courses_website.module.Module;
import com.internship.courses_website.video.controller.VideoController;
import com.internship.courses_website.video.entity.Video;
import com.internship.courses_website.video.service.VideoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VideoController.class)
public class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService videoService;

    @Autowired
    private ObjectMapper objectMapper;
    private String url1 = "https://www.youtube.com/watch?v=lS4wTuvR7Ik&ab_channel=Kinderlieder%2FWeihnachtsliedervonMuenchenmedia";
    private String url2 = "https://www.youtube.com/watch?v=_1UjFlp33Rc&ab_channel=KinderliederzumMitsingenundBewegen";

    private String title1 = "Christmas song 1";
    private String title2 = "Christmas song 2";

    @Test
    void testGetAllVideos() throws Exception {
        // Arrange
        Module mockModule = new Module();
        mockModule.setId(1L);

        List<Video> mockVideos = Arrays.asList(
                new Video(mockModule, url1, 120L, title1),
                new Video(mockModule, url2, 150L, title2));

        Mockito.when(videoService.getAllVideos(anyLong(), anyLong())).thenReturn(mockVideos);

        // Act & Assert
        mockMvc.perform(get("/api/courses/1/modules/1/videos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value(title1))
                .andExpect(jsonPath("$[0].url").value(url1))
                .andExpect(jsonPath("$[0].duration").value(120))
                .andExpect(jsonPath("$[1].title").value(title2))
                .andExpect(jsonPath("$[1].url").value(url2))
                .andExpect(jsonPath("$[1].duration").value(150));
    }

    @Test
    void testAddNewVideo() throws Exception {
        // Arrange
        Module mockModule = new Module();
        mockModule.setId(1L);

        Video newVideo = new Video(mockModule, url1, 200L, title1);
        Video savedVideo = new Video(mockModule, url2, 200L, title2);
        savedVideo.setId(1L);

        Mockito.when(videoService.addNewVideo(anyLong(), anyLong(), any(Video.class))).thenReturn(savedVideo);

        // Act & Assert
        mockMvc.perform(post("/api/courses/1/modules/1/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newVideo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(title2))
                .andExpect(jsonPath("$.url").value(url2))
                .andExpect(jsonPath("$.duration").value(200));
    }
}
