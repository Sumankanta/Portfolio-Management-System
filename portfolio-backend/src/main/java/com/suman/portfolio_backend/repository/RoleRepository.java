package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Role;
import com.suman.portfolio_backend.entity.enums.RoleName;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName roleName);

    boolean existsByRoleName(RoleName roleName);

    @Query("SELECT r FROM Role r ORDER BY r.roleName")
    List<Role> findAllOrderByName();

    @Query("SELECT r, COUNT(ur) FROM Role r LEFT JOIN r.userRoles ur GROUP BY r")
    List<Object[]> countUsersByRole();
}