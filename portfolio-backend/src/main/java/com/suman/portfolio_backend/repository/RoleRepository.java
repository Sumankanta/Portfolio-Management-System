package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}