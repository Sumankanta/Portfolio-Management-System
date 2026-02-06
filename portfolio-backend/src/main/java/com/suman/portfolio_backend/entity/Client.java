package com.suman.portfolio_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"projects"})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Client name is required")
    @Column(nullable = false)
    private String name;

    private String logoUrl;

    @URL(message = "Website URL must be valid")
    private String websiteUrl;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    // One-to-Many relationship with Projects
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties({"client"})
    private List<Project> projects = new ArrayList<>();

//    Helper methods for managing projects relationship

    public void addProject(Project project){
        projects.add(project);
        project.setClient(this);
    }

    public void removeProject(Project project){
        projects.remove(project);
        project.setClient(null);
    }

//    get project count
    public int getProjectCount(){
        return projects != null ? projects.size() : 0;
    }

//   this is used to check if the client has projects
    public boolean hasProjects(){
        return projects != null && !projects.isEmpty();
    }
}
