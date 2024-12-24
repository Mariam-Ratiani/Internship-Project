package com.internship.courses_website.video.entity;

import org.hibernate.annotations.Collate;
import com.internship.courses_website.module.Module;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Column(name = "title")
    private String title;
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "duration", nullable = false)
    private Long duration;

    public Video() {
    }

    public Video(Module module, String url) {
        this.module = module;
        this.title = title;
        this.url = url;
    }

    public Video(Module module, String url, Long duration, String title) {
        this.module = module;
        this.title = title;
        this.url = url;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public Module getModule() {
        return module;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
