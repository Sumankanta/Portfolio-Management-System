package com.suman.portfolio_backend.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "ui_settings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UISettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private Boolean darkMode = false;

    private Boolean animationsEnabled = true;

    private String layoutPreference;

    private String language;
}

