package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}