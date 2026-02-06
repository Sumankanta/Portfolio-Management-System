package com.suman.portfolio_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"employments", "userRoles", "uiSettings", "chatMessages", "payments"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false)
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Email should be valid")
    @Column(nullable = false)
    private String passwordHash;

    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String profileImageUrl;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String contactNumber;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relationships

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<PastEmployment> employments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UISettings uiSettings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    // Helper methods for bidirectional relationships

    public void addEmployment(PastEmployment employment){
        employments.add(employment);
        employment.setUser(this);
    }

    public void removeEmployment(PastEmployment employment){
        employments.remove(employment);
        employment.setUser(null);
    }

    public void addUserRole(UserRole userRole){
        userRoles.add(userRole);
        userRole.setUser(this);
    }

    public void removeUserRole(UserRole userRole){
        userRoles.remove(userRole);
        userRole.setUser(null);
    }

    public void addChatMessage(ChatMessage chatMessage){
        chatMessages.add(chatMessage);
        chatMessage.setUser(this);
    }

    public void removeChatMessage(ChatMessage chatMessage){
        chatMessages.remove(chatMessage);
        chatMessage.setUser(null);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setUser(this);
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
        payment.setUser(null);
    }

    public void setUiSetting(UISettings uiSettings){

        if(uiSettings == null) {
            if (this.uiSettings != null) {
                this.uiSettings.setUser(null);
            }
        }else{
            uiSettings.setUser(this);
        }
        this.uiSettings = uiSettings;
    }

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}

