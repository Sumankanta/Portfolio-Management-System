package com.suman.portfolio_backend.controller;

import com.suman.portfolio_backend.dto.ProjectDTO;
import com.suman.portfolio_backend.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO){

        log.info("REST request to create Project with title: {}", projectDTO.getTitle());
        ProjectDTO created = projectService.createProject(projectDTO);
        log.info("Project created successfully with ID: {}", created.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDTO>> getAll(){
        log.debug("REST request to fetch all Projects");
        List<ProjectDTO> projects = projectService.getAllProjects();
        log.info("Fetched {} projects", projects.size());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable Long id){
        log.debug("REST request to fetch Project with ID: {}", id);
        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable Long id, @RequestBody ProjectDTO projectDTO){
        log.info("REST request to update Project with ID: {}", id);
        ProjectDTO update = projectService.updateProject(id, projectDTO);
        log.info("Project updated successfully with Id: {}", id);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.warn("REST request to delete Project with ID: {}", id);
        projectService.deletedProject(id);
        log.info("Project deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
