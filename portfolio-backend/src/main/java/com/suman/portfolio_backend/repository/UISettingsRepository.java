package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.UISettings;
import com.suman.portfolio_backend.entity.User;
import com.suman.portfolio_backend.entity.enums.Language;
import com.suman.portfolio_backend.entity.enums.LayoutPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UISettingsRepository extends JpaRepository<UISettings, Long> {

    Optional<UISettings> findByUser(User user);

    Optional<UISettings> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    List<UISettings> findByDarkModeTrue();

    List<UISettings> findByDarkModeFalse();

    List<UISettings> findByAnimationsEnabledTrue();

    List<UISettings> findByAnimationsEnabledFalse();

    List<UISettings> findByLayoutPreference(LayoutPreference layoutPreference);

    List<UISettings> findByLanguage(Language language);

    @Query("SELECT COUNT(ui) FROM UISettings ui WHERE ui.darkMode = true")
    long countDarkModeUsers();

    @Query("SELECT COUNT(ui) FROM UISettings ui WHERE ui.animationsEnabled = true")
    long countAnimationsEnabledUsers();

    @Query("SELECT ui.layoutPreference, COUNT(ui) FROM UISettings ui GROUP BY ui.layoutPreference")
    List<Object[]> countByLayoutPreference();

    @Query("SELECT ui.language, COUNT(ui) FROM UISettings ui GROUP BY ui.language ORDER BY COUNT(ui) DESC")
    List<Object[]> countByLanguage();

    @Query("SELECT ui.layoutPreference FROM UISettings ui GROUP BY ui.layoutPreference " +
            "ORDER BY COUNT(ui) DESC LIMIT 1")
    LayoutPreference findMostPopularLayout();

    @Query("SELECT ui.language FROM UISettings ui GROUP BY ui.language " +
            "ORDER BY COUNT(ui) DESC LIMIT 1")
    Language findMostPopularLanguage();

    void deleteByUserId(Long userId);

    @Query("SELECT ui FROM UISettings ui WHERE ui.darkMode = :darkMode AND ui.animationsEnabled = :animationsEnabled")
    List<UISettings> findByPreferences(
            @Param("darkMode") Boolean darkMode,
            @Param("animationsEnabled") Boolean animationsEnabled
    );

    @Query("SELECT ui.darkMode, ui.animationsEnabled, ui.layoutPreference, ui.language, COUNT(ui) " +
            "FROM UISettings ui GROUP BY ui.darkMode, ui.animationsEnabled, ui.layoutPreference, ui.language")
    List<Object[]> getPreferencesStatistics();
}