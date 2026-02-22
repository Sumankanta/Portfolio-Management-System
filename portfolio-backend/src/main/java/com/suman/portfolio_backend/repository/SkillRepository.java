package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByCategory(String category);

    List<Skill> findByCategoryIgnoreCase(String category);

    List<Skill> findByNameContainingIgnoreCase(String keyword);

    List<Skill> findByProficiencyLevelGreaterThanEqual(Integer proficiency);

    List<Skill> findByProficiencyLevelBetween(Integer minProficiency, Integer maxProficiency);

    @Query("SELECT DISTINCT s.category FROM Skill s ORDER BY s.category")
    List<String> findAllCategories();

    @Query("SELECT s FROM Skill s ORDER BY s.proficiencyLevel DESC")
    List<Skill> findAllOrderedByProficiency();

    @Query("SELECT DISTINCT s FROM Skill s WHERE SIZE(s.projects) > 0")
    List<Skill> findSkillsUsedInProjects();

    @Query("SELECT s FROM Skill s WHERE SIZE(s.projects) = 0")
    List<Skill> findUnusedSkills();

    @Query("SELECT s, SIZE(s.projects) as projectCount FROM Skill s ORDER BY SIZE(s.projects) DESC")
    List<Object[]> findSkillsWithProjectCount();

    @Query(value = "SELECT s.* FROM skills s " +
            "LEFT JOIN project_skills ps ON s.id = ps.skill_id " +
            "GROUP BY s.id ORDER BY COUNT(ps.project_id) DESC LIMIT :limit",
            nativeQuery = true)
    List<Skill> findTopSkillsByUsage(@Param("limit") int limit);

    @Query("SELECT s.category, COUNT(s) FROM Skill s GROUP BY s.category ORDER BY COUNT(s) DESC")
    List<Object[]> countSkillsByCategory();

    @Query("SELECT s FROM Skill s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(s.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Skill> searchByNameOrCategory(@Param("searchTerm") String searchTerm);

    @Query("SELECT s FROM Skill s WHERE s.category = :category ORDER BY s.proficiencyLevel DESC")
    List<Skill> findByCategoryOrderedByProficiency(@Param("category") String category);
}