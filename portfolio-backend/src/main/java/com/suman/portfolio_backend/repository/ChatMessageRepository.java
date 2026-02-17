package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.ChatMessage;
import com.suman.portfolio_backend.entity.enums.SenderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findBySessionId(String sessionId);

    List<ChatMessage> findBySessionIdOrderByTimestampAsc(String sessionId);

    List<ChatMessage> findByUserId(Long userId);

    List<ChatMessage> findBySenderType(SenderType senderType);

    List<ChatMessage> findBySessionIdAndSenderType(String sessionId, SenderType senderType);

    List<ChatMessage> findByIsReadFalse();

    List<ChatMessage> findBySessionIdAndIsReadFalse(String sessionId);

    List<ChatMessage> findBySenderTypeAndIsReadFalse(SenderType senderType);

    @Query("SELECT COUNT(cm) FROM ChatMessage cm WHERE cm.isRead = false")
    long countUnreadMessages();

    @Query("SELECT COUNT(cm) FROM ChatMessage cm WHERE cm.senderType = :senderType AND cm.isRead = false")
    long countUnreadMessagesBySenderType(@Param("senderType") SenderType senderType);

    @Query("SELECT COUNT(cm) FROM ChatMessage cm WHERE cm.sessionId = :sessionId AND cm.isRead = false")
    long countUnreadMessagesBySession(@Param("sessionId") String sessionId);

    List<ChatMessage> findByTimestampAfter(LocalDateTime timestamp);

    List<ChatMessage> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.timestamp >= :timestamp ORDER BY cm.timestamp DESC")
    List<ChatMessage> findRecentMessages(@Param("timestamp") LocalDateTime timestamp);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.sessionId = :sessionId ORDER BY cm.timestamp DESC LIMIT 1")
    ChatMessage findLatestMessageBySession(@Param("sessionId") String sessionId);

    @Query("SELECT DISTINCT cm.sessionId FROM ChatMessage cm ORDER BY cm.sessionId")
    List<String> findAllDistinctSessions();

    @Query("SELECT DISTINCT cm.sessionId FROM ChatMessage cm WHERE cm.timestamp >= :timestamp")
    List<String> findActiveSessionsSince(@Param("timestamp") LocalDateTime timestamp);

    @Query("SELECT COUNT(cm) FROM ChatMessage cm WHERE cm.sessionId = :sessionId")
    long countMessagesBySession(@Param("sessionId") String sessionId);

    @Query("SELECT COUNT(cm) FROM ChatMessage cm WHERE cm.senderType = :senderType")
    long countMessagesBySenderType(@Param("senderType") SenderType senderType);

    void deleteByTimestampBefore(LocalDateTime date);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.sessionId = :sessionId AND cm.senderType = 'AI' ORDER BY cm.timestamp ASC")
    List<ChatMessage> findAIMessagesBySession(@Param("sessionId") String sessionId);
}