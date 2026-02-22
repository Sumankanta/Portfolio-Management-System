package com.suman.portfolio_backend.service.Impl;

import com.suman.portfolio_backend.dto.ProjectDTO;
import com.suman.portfolio_backend.entity.*;
import com.suman.portfolio_backend.repository.*;
import com.suman.portfolio_backend.service.interfaces.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {

        log.info("Creating new project with title: {}", projectDTO.getTitle());
        Project project = modelMapper.map(projectDTO, Project.class);

        if (projectDTO.getClientId() != null) {
            log.debug("Fetching client with ID: {}", projectDTO.getClientId());
            Client client = clientRepository.findById(projectDTO.getClientId())
                    .orElseThrow(() -> {
                        log.error("Client not found with ID: {}", projectDTO.getClientId());
                        return new RuntimeException("Client not found");
                    });
            project.setClient(client);
        }

        if (projectDTO.getSkillIds() != null && !projectDTO.getSkillIds().isEmpty()) {
            log.debug("Fetching skills for IDs: {}", projectDTO.getSkillIds());
            Set<Skill> skill = new HashSet<>(skillRepository.findAllById(projectDTO.getSkillIds()));
            project.setSkills(skill);
        }

        Project savedProject = projectRepository.save(project);
        log.info("Project created successfully with ID: {}", savedProject.getId());
        return convertToDTO(savedProject);
    }


    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        log.info("Updating project with ID: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Project not found with ID: {}", id);
                    return new RuntimeException("Project not found");
                });

        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setThumbnailUrl(projectDTO.getThumbnailUrl());
        project.setLiveDemoUrl(projectDTO.getLiveDemoUrl());
        project.setSourceCodeUrl(projectDTO.getSourceCodeUrl());

        log.info("Project updated successfully with ID: {}", id);

        return convertToDTO(project);
    }

    @Override
    public void deletedProject(Long id) {
        log.info("Deleting project with ID: {}", id);
        if (!projectRepository.existsById(id)) {
            log.warn("Attempted to deleteEmployment non-existing project with ID: {}", id);
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
        log.info("Project deleted successfully with ID: {}", id);
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        log.debug("Fetching project with ID: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Project not found with ID: {}", id);
                    return new RuntimeException("Project not found");
                });
        return convertToDTO(project);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        log.debug("Fetching all projects");
        List<ProjectDTO> projects = projectRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();

        log.info("Total projects fetched: {}", projects.size());
        return projects;
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);

        if (project.getClient() != null) {
            projectDTO.setClientId(project.getClient().getId());
            projectDTO.setClientName(project.getClient().getName());
        }

        if (project.getSkills() != null && !project.getSkills().isEmpty()) {
            projectDTO.setSkillIds(project.getSkills()
                    .stream()
                    .map(Skill::getId)
                    .collect(Collectors.toSet()));

            projectDTO.setSkillNames(project.getSkills()
                    .stream()
                    .map(Skill::getName)
                    .collect(Collectors.toSet()));
        }
        return projectDTO;
    }
}
