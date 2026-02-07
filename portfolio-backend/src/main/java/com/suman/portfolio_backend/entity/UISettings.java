package com.suman.portfolio_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.suman.portfolio_backend.entity.enums.Language;
import com.suman.portfolio_backend.entity.enums.LayoutPreference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ui_settings", indexes = {
        @Index(name = "idx_ui_user", columnList = "user_id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user"})
public class UISettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnoreProperties({"uiSettings", "chatMessages", "employments", "userRoles", "payments"})
    private User user;

    @Column(nullable = false)
    private Boolean darkMode = false;

    @Column(nullable = false)
    private Boolean animationsEnabled = true;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LayoutPreference layoutPreference;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Language language;

    @PrePersist
    protected void onCreate() {
        if (darkMode == null) {
            darkMode = false;
        }
        if (animationsEnabled == null) {
            animationsEnabled = true;
        }
        if (language == null) {
            language = Language.EN;
        }
        if (layoutPreference == null) {
            layoutPreference = LayoutPreference.GRID;
        }
    }

    // Helper methods for theme management
    public void enableDarkMode() {
        this.darkMode = true;
    }

    public void disableDarkMode() {
        this.darkMode = false;
    }

    public void toggleDarkMode() {
        this.darkMode = !this.darkMode;
    }

    public void enableAnimations() {
        this.animationsEnabled = true;
    }

    public void disableAnimations() {
        this.animationsEnabled = false;
    }

    public void toggleAnimations() {
        this.animationsEnabled = !this.animationsEnabled;
    }

    // Helper methods for layout
    public boolean isGridLayout() {
        return layoutPreference == LayoutPreference.GRID;
    }

    public boolean isListLayout() {
        return layoutPreference == LayoutPreference.LIST;
    }

    public boolean isCardLayout() {
        return layoutPreference == LayoutPreference.CARD;
    }

    public boolean isMasonryLayout() {
        return layoutPreference == LayoutPreference.MASONRY;
    }
}