package com.suman.portfolio_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    @ManyToOne
    private User user;

    @NotBlank
    private String messageContent;

    @NotBlank
    private String senderType; // USER, ADMIN, AI

    private Boolean isRead = false;

    private LocalDateTime timestamp = LocalDateTime.now();
}
