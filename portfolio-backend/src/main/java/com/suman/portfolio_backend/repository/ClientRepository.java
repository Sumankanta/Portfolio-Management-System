package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}