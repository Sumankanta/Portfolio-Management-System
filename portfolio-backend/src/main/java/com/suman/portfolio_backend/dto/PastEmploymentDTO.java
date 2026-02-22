package com.suman.portfolio_backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PastEmploymentDTO {

    private Long id;

    private Long userId;

    private String companyName;
    private String jobRole;

    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean isCurrent;

    private String description;


    private String formattedDuration;
    private long durationInMonths;
}