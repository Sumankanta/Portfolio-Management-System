package com.suman.portfolio_backend.service.interfaces;

import com.suman.portfolio_backend.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    ProjectDTO createProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(Long id, ProjectDTO projectDTO);
    void deletedProject(Long id);
    ProjectDTO getProjectById(Long id);
    List<ProjectDTO> getAllProjects();
}
