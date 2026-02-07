package com.suman.portfolio_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String passwordHash;

    @NotBlank
    private String fullName;

    private String bio;

    private String profileImageUrl;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String contactNumber;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user")
    private List<PastEmployment> employments;
}
