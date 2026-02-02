package com.suman.portfolio_backend.service;

import com.suman.portfolio_backend.entity.Project;
import com.suman.portfolio_backend.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    public Project save(Project project){
        return projectRepository.save(project);
    }

    public void delete(Long id){
        projectRepository.deleteById(id);
    }
}
