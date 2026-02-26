package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.User;
import com.suman.portfolio_backend.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByFullNameContainingIgnoreCase(String keyword);

//    List<User> findByCreatedAtAfter(LocalDateTime date);
//
//    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
//
//    @Query("SELECT DISTINCT u FROM User u JOIN u.userRoles ur JOIN ur.role r WHERE r.roleName = :roleName")
//    List<User> findByRoleName(@Param("roleName") RoleName roleName);
//
//    @Query("Select Count(u) from User u")
//    long countTotalUsers();

//    @Query("Select DISTINCT u From User u WHERE SIZE(u.employments) > 0")
//    List<User> findUserWithEmploymentHistory();

//    @Query("SELECT DISTINCT u FROM User u WHERE SIZE(u.payments) > 0")
//    List<User> findUserWithPayments();
//
//    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
//            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
//    List<User> searchByUsernameOrEmail(@Param("searchTerm") String searchTerm);
//  unnecessary method
//    @Query("SELECT u FROM User u WHERE u.createdAt >= :date ORDER BY u.createdAt DESC")
//    List<User> findRecentUser(@Param("date") LocalDateTime date);
}