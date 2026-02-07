package com.suman.portfolio_backend.entity;

import com.suman.portfolio_backend.entity.enums.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Role name is required")
    @Column(unique = true, nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    private String description;

//    Helper method
    public boolean isAdmin(){
        return roleName == RoleName.ROLE_ADMIN;
    }

    public boolean isUser(){
        return roleName == RoleName.ROLE_USER;
    }

    public boolean isModerator(){
        return roleName == RoleName.ROLE_MODERATOR;
    }
}