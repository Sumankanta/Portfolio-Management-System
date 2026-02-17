package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByClientId(Long clientId);

    List<Project> findByTitleContainingIgnoreCase(String keyword);

    List<Project> findByCreatedAtAfter(LocalDateTime date);

    List<Project> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT DISTINCT p FROM Project p JOIN p.skills s WHERE s.id = :skillId")
    List<Project> findBySkillId(@Param("skillId") Long skillId);

    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.skills WHERE p.id = :id")
    Optional<Project> findByIdWithSkills(@Param("id") Long id);

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.client WHERE p.id = :id")
    Optional<Project> findByIdWithClient(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.skills")
    List<Project> findAllWithSkills();

    @Query("SELECT p FROM Project p JOIN p.skills s WHERE s.id IN :skillIds GROUP BY p HAVING COUNT(s) >= :count")
    List<Project> findBySkillIds(@Param("skillIds") List<Long> skillIds, @Param("count") long count);

    @Query("SELECT p FROM Project p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Project> searchByTitleOrDescription(@Param("searchTerm") String searchTerm);

    @Query("SELECT COUNT(p) FROM Project p")
    long countTotalProjects();

    @Query("SELECT p FROM Project p WHERE p.client IS NULL")
    List<Project> findProjectsWithoutClient();

    @Query("SELECT p FROM Project p WHERE p.liveDemoUrl IS NOT NULL")
    List<Project> findProjectsWithLiveDemo();

    @Query("SELECT p FROM Project p WHERE LOWER(p.client.name) LIKE LOWER(CONCAT('%', :clientName, '%'))")
    List<Project> findByClientName(@Param("clientName") String clientName);
}