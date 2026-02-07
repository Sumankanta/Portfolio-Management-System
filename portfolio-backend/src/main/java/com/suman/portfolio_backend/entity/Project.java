package com.suman.portfolio_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"skills", "client"})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project title is required")
    @Column(nullable = false)
    private String title;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnailUrl;

    private String liveDemoUrl;

    private String sourceCodeUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

//    Relationships

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties({"projects"})
    private Client client;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_skills",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "skill_id"})
    )
    @Builder.Default
    @JsonIgnoreProperties({"projects"})
    private List<Skill> skills = new ArrayList<>();

    // Helper methods for managing skills relationship

    public void addSkill(Skill skill) {
        if (!this.skills.contains(skill)) {
            this.skills.add(skill);
            skill.getProjects().add(this);
        }
    }

    public void removeSkill(Skill skill) {
        if (this.skills.contains(skill)) {
            this.skills.remove(skill);
            skill.getProjects().remove(this);
        }
    }

    public void clearSkills() {
        for (Skill skill : new ArrayList<>(this.skills)) {
            removeSkill(skill);
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
