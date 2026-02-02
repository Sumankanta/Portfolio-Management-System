package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}