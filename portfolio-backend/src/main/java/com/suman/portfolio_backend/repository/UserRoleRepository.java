package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Role;
import com.suman.portfolio_backend.entity.User;
import com.suman.portfolio_backend.entity.UserRole;
import com.suman.portfolio_backend.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUser(User user);

    List<UserRole> findByUserId(Long userId);

    List<UserRole> findByRole(Role role);

    List<UserRole> findByRoleId(Long roleId);

    Optional<UserRole> findByUserAndRole(User user, Role role);

    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    void deleteByUserIdAndRoleId(Long userId, Long roleId);

    void deleteByUserId(Long userId);

    @Query("SELECT ur FROM UserRole ur WHERE ur.role.roleName = :roleName")
    List<UserRole> findByRoleName(@Param("roleName") RoleName roleName);

    @Query("SELECT COUNT(ur) FROM UserRole ur WHERE ur.user.id = :userId")
    long countRolesByUserId(@Param("userId") Long userId);
}