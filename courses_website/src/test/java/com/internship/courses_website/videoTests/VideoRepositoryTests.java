package com.internship.courses_website.videoTests;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.internship.courses_website.video.entity.Video;
import com.internship.courses_website.video.repository.VideoRepository;
import com.internship.courses_website.categories.entity.Category;
import com.internship.courses_website.categories.repository.CategoryRepository;
import com.internship.courses_website.courses.entity.Course;
import com.internship.courses_website.courses.repository.CoursesRepository;
import com.internship.courses_website.module.Module;
import com.internship.courses_website.module.ModuleRepository;

@DataJpaTest
public class VideoRepositoryTests {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Module module1;

    private final String videoUrl1 = "https://www.youtube.com/watch?v=_1UjFlp33Rc&ab_channel=KinderliederzumMitsingenundBewegen";
    private final String videoUrl2 = "https://www.youtube.com/watch?v=lS4wTuvR7Ik&ab_channel=Kinderlieder%2FWeihnachtsliedervonMuenchenmedia";
    private final String videoTitle1 = "Kinderlieder Video 1";
    private final String videoTitle2 = "Kinderlieder Video 2";

    @BeforeEach
    void setup() {
        // Save category
        Category programming = new Category("Programming", "Courses related to programming");
        categoryRepository.save(programming);

        // Save course
        Course course1 = new Course("Java Basics", "Learn Java programming", programming);
        coursesRepository.save(course1);

        // Save module and assign it to class-level variable
        module1 = new Module(course1, "Introduction", "Getting started with Java", 1);
        module1 = moduleRepository.save(module1);
    }

    @Test
    public void testFindAllVideosByModuleId() {
        // Create and save videos associated with the saved module
        Video video1 = new Video(module1, videoUrl1, 180L, videoTitle1);
        Video video2 = new Video(module1, videoUrl2, 200L, videoTitle2);
        videoRepository.saveAll(List.of(video1, video2));

        // Fetch videos by the saved module's ID
        List<Video> retrievedVideos = videoRepository.findByModule_Id(module1.getId());

        // Assert that the correct videos are retrieved
        assertThat(retrievedVideos).hasSize(2);
        assertThat(retrievedVideos)
                .extracting(Video::getUrl)
                .containsExactlyInAnyOrder(videoUrl1, videoUrl2);
        assertThat(retrievedVideos)
                .extracting(Video::getTitle)
                .containsExactlyInAnyOrder(videoTitle1, videoTitle2);
    }

    @Test
    public void testNoVideosForModuleId() {
        // Fetch videos for a module with no videos
        List<Video> retrievedVideos = videoRepository.findByModule_Id(module1.getId());

        // Assert that no videos are retrieved
        assertThat(retrievedVideos).isEmpty();
    }

    @Test
    public void testSaveVideo() {
        // Create a new video
        Video video = new Video(module1, videoUrl1, 180L, videoTitle1);

        // Save the video
        Video savedVideo = videoRepository.save(video);

        // Assert that the video is saved correctly
        assertThat(savedVideo.getId()).isNotNull();
        assertThat(savedVideo.getUrl()).isEqualTo(videoUrl1);
        assertThat(savedVideo.getTitle()).isEqualTo(videoTitle1);
        assertThat(savedVideo.getModule()).isEqualTo(module1);
    }

    @Test
    public void testDeleteVideo() {
        // Create and save a video
        Video video = new Video(module1, videoUrl1, 180L, videoTitle1);
        video = videoRepository.save(video);

        // Delete the video
        videoRepository.deleteById(video.getId());

        // Assert that the video is no longer in the repository
        assertThat(videoRepository.findById(video.getId())).isEmpty();
    }

    @Test
    public void testFindByNonexistentModuleId() {
        // Fetch videos for a nonexistent module ID
        List<Video> retrievedVideos = videoRepository.findByModule_Id(999L);

        // Assert that no videos are retrieved
        assertThat(retrievedVideos).isEmpty();
    }
}
