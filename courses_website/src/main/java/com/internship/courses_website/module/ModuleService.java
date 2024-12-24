package com.internship.courses_website.module;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public Module getModuleById(Long courseId, Long moduleId) {
        return moduleRepository.findByCourse_CourseIdAndId(courseId, moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found by its id"));
    }

    public List<Module> getModulesByCourse(Long courseId) {
        return moduleRepository.findByCourse_CourseId(courseId);
    }

    public Module getModuleByCourseAndPosition(Long courseId, Integer position) {
        return moduleRepository
                .findByCourse_CourseIdAndPosition(courseId, position)
                .orElseThrow(() -> new RuntimeException("Module not found by its position"));
    }
}
