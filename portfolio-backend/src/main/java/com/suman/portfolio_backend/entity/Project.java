package com.suman.portfolio_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Size(max = 2000)
    private String description;

    private String thumbnailUrl;
    private String liveDemoUrl;
    private String sourceCodeUrl;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private LocalDateTime createdAt = LocalDateTime.now();
}
