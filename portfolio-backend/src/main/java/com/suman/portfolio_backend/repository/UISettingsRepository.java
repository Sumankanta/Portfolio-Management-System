package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.UISettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UISettingsRepository extends JpaRepository<UISettings, Long> {
}