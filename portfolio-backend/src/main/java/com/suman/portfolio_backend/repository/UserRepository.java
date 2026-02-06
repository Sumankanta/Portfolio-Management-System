package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}