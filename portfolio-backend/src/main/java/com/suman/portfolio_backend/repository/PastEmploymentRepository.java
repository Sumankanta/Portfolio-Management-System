package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.PastEmployment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PastEmploymentRepository extends JpaRepository<PastEmployment, Long> {
}