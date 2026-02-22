package com.suman.portfolio_backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ProjectDTO {

    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String liveDemoUrl;
    private String sourceCodeUrl;
    private Long clientId;
    private String clientName;
    private Set<Long> skillIds;
    private Set<String> skillNames;
    private LocalDateTime createdAt;
}
