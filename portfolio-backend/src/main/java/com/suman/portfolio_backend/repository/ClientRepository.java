package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByName(String name);

    List<Client> findByNameContainingIgnoreCase(String keyword);

    boolean existsByName(String name);

    @Query("SELECT c FROM Client c WHERE c.websiteUrl IS NOT NULL")
    List<Client> findClientsWithWebsite();

    @Query("SELECT c FROM Client c WHERE c.websiteUrl IS NULL")
    List<Client> findClientsWithoutWebsite();

    @Query("SELECT DISTINCT c FROM Client c WHERE SIZE(c.projects) > 0")
    List<Client> findClientWithProjects();

    @Query("SELECT c FROM Client c WHERE SIZE(c.projects) = 0")
    List<Client> findClientWithoutProjects();

    @Query("SELECT DISTINCT c FROM Client c LEFT JOIN FETCH c.projects WHERE c.id = :id")
    Optional<Client> findByIdWithProjects(@Param("id") Long id);

    @Query("SELECT DISTINCT c FROM Client c LEFT JOIN FETCH c.projects")
    List<Client> findAllWithProjects();

    @Query("SELECT c, SIZE(c.projects) as projectCount FROM Client c ORDER BY SIZE(c.projects) DESC")
    List<Object[]> findClientsWithProjectCount();

    @Query("SELECT c FROM Client c ORDER BY SIZE(c.projects) DESC")
    List<Client> findTopClientsByProjectCount(@Param("limit") int limit);

    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Client> searchByNameOrDescription(@Param("searchTerm") String searchTerm);

    @Query("SELECT COUNT(c) FROM Client c")
    long countTotalClients();

    List<Client> findAllByOrderByNameAsc();
}