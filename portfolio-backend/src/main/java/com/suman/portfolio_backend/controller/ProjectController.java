package com.suman.portfolio_backend.controller;

import com.suman.portfolio_backend.entity.Project;
import com.suman.portfolio_backend.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> all(){
        return projectService.getAll();
    }

    @PostMapping
    public Project create(@RequestBody @Valid Project project){
        return projectService.save(project);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        projectService.delete(id);
    }
}
