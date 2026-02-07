package com.suman.portfolio_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.suman.portfolio_backend.entity.enums.SenderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages", indexes = {
        @Index(name = "idx_chat_session", columnList = "sessionId"),
        @Index(name = "idx_chat_user", columnList = "user_id"),
        @Index(name = "idx_chat_timestamp", columnList = "timestamp"),
        @Index(name = "idx_chat_sender_type", columnList = "senderType")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user"})
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Session ID is required")
    @Column(nullable = false)
    private String sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"chatMessages", "employments", "userRoles", "payments"})
    private User user;

    @NotBlank(message = "Message content is required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String messageContent;

    @NotNull(message = "Sender type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SenderType senderType;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
        if (isRead == null) {
            isRead = false;
        }
    }

    public boolean isFromAI() {
        return senderType == SenderType.AI;
    }

    public boolean isFromAdmin() {
        return senderType == SenderType.ADMIN;
    }

    public boolean isFromUser() {
        return senderType == SenderType.USER;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public void markAsUnread() {
        this.isRead = false;
    }
}