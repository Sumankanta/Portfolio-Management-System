package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.PastEmployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PastEmploymentRepository extends JpaRepository<PastEmployment, Long> {

    List<PastEmployment> findByUserId(Long userId);

    List<PastEmployment> findByUserIdAndIsCurrentTrue(Long userId);

    List<PastEmployment> findByUserIdAndIsCurrentFalse(Long userId);

    List<PastEmployment> findByCompanyNameContainingIgnoreCase(String companyName);

    List<PastEmployment> findByJobRoleContainingIgnoreCase(String jobRole);

    List<PastEmployment> findByIsCurrentTrue();

    @Query("SELECT pe FROM PastEmployment pe WHERE pe.user.id = :userId ORDER BY pe.startDate DESC")
    List<PastEmployment> findByUserIdOrderByStartDateDesc(@Param("userId") Long userId);

    List<PastEmployment> findByStartDateAfter(LocalDate date);

    List<PastEmployment> findByEndDateBefore(LocalDate date);

    @Query("SELECT pe FROM PastEmployment pe WHERE pe.startDate <= :endDate AND " +
            "(pe.endDate IS NULL OR pe.endDate >= :startDate)")
    List<PastEmployment> findEmploymentsInDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COUNT(pe) FROM PastEmployment pe WHERE pe.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(pe) FROM PastEmployment pe WHERE pe.user.id = :userId AND pe.isCurrent = true")
    long countCurrentEmploymentsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM past_employment " +
            "WHERE user_id = :userId " +
            "ORDER BY (COALESCE(end_date, CURRENT_DATE) - start_date) DESC",
            nativeQuery = true)
    List<PastEmployment> findByUserIdOrderByDuration(@Param("userId") Long userId);

    @Query("SELECT pe FROM PastEmployment pe WHERE " +
            "LOWER(pe.companyName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(pe.jobRole) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<PastEmployment> searchByCompanyOrRole(@Param("searchTerm") String searchTerm);

    @Query("SELECT DISTINCT pe.companyName FROM PastEmployment pe ORDER BY pe.companyName")
    List<String> findAllDistinctCompanies();

    @Query("SELECT DISTINCT pe.jobRole FROM PastEmployment pe ORDER BY pe.jobRole")
    List<String> findAllDistinctJobRoles();
}