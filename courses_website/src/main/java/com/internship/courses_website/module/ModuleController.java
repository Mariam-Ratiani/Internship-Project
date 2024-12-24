package com.internship.courses_website.module;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/{courseId}/modules")
public class ModuleController {

    private final ModuleService moduleService;

    ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping("/{moduleId}")
    public Module getModuleById(@PathVariable Long courseId, @PathVariable Long moduleId) {
        return moduleService.getModuleById(courseId, moduleId);
    }

    @GetMapping
    public List<Module> getModulesByCourse(@PathVariable Long courseId) {
        return moduleService.getModulesByCourse(courseId);
    }

    @GetMapping("/position/{position}")
    public Module getModuleByCourseAndPosition(@PathVariable Long courseId, @PathVariable Integer position) {
        return moduleService.getModuleByCourseAndPosition(courseId, position);
    }

}
