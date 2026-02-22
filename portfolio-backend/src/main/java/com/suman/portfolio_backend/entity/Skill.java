package com.suman.portfolio_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skills", indexes = {
        @Index(name = "idx_skill_category", columnList = "category"),
        @Index(name = "idx_skill_name", columnList = "name")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"projects"})
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Category is required")
    @Column(nullable = false)
    private String category;

    @Min(value = 0, message = "Proficiency level must be at least 0")
    @Max(value = 100, message = "Proficiency level must not exceed 100")
    @Column(nullable = false)
    private Integer proficiencyLevel;

    private String iconUrl;

    // Many-to-Many relationship with Projects (inverse side)
    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties({"skills"})
    private List<Project> projects = new ArrayList<>();

    // Helper method to get project count
    public int getProjectCount() {
        return projects != null ? projects.size() : 0;
    }

    // Helper method to check if skill is used in any project
    public boolean isUsedInProjects() {
        return projects != null && !projects.isEmpty();
    }
}
