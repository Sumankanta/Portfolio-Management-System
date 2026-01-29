package com.suman.portfolio_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "past_employment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PastEmployment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @NotBlank
    private String companyName;

    @NotBlank
    private String jobRole;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isCurrent = false;

    @Size(max = 1000)
    private String description;
}

