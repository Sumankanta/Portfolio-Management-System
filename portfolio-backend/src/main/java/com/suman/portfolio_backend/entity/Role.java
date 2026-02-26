package com.suman.portfolio_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.suman.portfolio_backend.entity.enums.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"userRoles"})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Role name is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private RoleName roleName;

    private String description;

    // Relationship with UserRole
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnoreProperties({"role"})
    private List<UserRole> userRoles = new ArrayList<>();

    // Helper methods
    public boolean isAdmin() {
        return roleName == RoleName.ROLE_ADMIN;
    }

    public boolean isUser() {
        return roleName == RoleName.ROLE_USER;
    }

    public boolean isModerator() {
        return roleName == RoleName.ROLE_MODERATOR;
    }

    // Helper method to add user role
    public void addUserRole(UserRole userRole) {
        userRoles.add(userRole);
        userRole.setRole(this);
    }

    // Helper method to remove user role
    public void removeUserRole(UserRole userRole) {
        userRoles.remove(userRole);
        userRole.setRole(null);
    }

    // Get count of users with this role
    public int getUserCount() {
        return userRoles != null ? userRoles.size() : 0;
    }


}