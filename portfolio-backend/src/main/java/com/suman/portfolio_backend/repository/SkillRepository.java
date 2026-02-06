package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}