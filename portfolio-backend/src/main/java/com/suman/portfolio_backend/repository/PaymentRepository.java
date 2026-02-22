package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Payment;
import com.suman.portfolio_backend.entity.enums.PaymentMethod;
import com.suman.portfolio_backend.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUserId(Long userId);

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    List<Payment> findByUserIdAndStatus(Long userId, PaymentStatus status);

    List<Payment> findByCurrency(String currency);

    @Query("SELECT p FROM Payment p WHERE p.user.id = :userId ORDER BY p.timestamp DESC")
    List<Payment> findByUserIdOrderByTimestampDesc(@Param("userId") Long userId);

    List<Payment> findByTimestampAfter(LocalDateTime timestamp);

    List<Payment> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT p FROM Payment p WHERE p.status = :status AND p.timestamp BETWEEN :startTime AND :endTime")
    List<Payment> findByStatusAndTimestampBetween(
            @Param("status") PaymentStatus status,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") PaymentStatus status);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.user.id = :userId AND p.status = :status")
    BigDecimal sumAmountByUserAndStatus(@Param("userId") Long userId, @Param("status") PaymentStatus status);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.currency = :currency AND p.status = :status")
    BigDecimal sumAmountByCurrencyAndStatus(@Param("currency") String currency, @Param("status") PaymentStatus status);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status")
    long countByStatus(@Param("status") PaymentStatus status);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentMethod = :paymentMethod")
    long countByPaymentMethod(@Param("paymentMethod") PaymentMethod paymentMethod);

    List<Payment> findByAmountGreaterThanEqual(BigDecimal amount);

    List<Payment> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    @Query("SELECT p FROM Payment p WHERE p.status = 'SUCCESS' AND p.timestamp >= :timestamp ORDER BY p.timestamp DESC")
    List<Payment> findRecentSuccessfulPayments(@Param("timestamp") LocalDateTime timestamp);

    @Query("SELECT p FROM Payment p WHERE p.status = 'FAILED' ORDER BY p.timestamp DESC")
    List<Payment> findFailedPayments();

    @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' AND p.timestamp < :timestamp")
    List<Payment> findStalePendingPayments(@Param("timestamp") LocalDateTime timestamp);

    @Query("SELECT p.paymentMethod, COUNT(p), COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "WHERE p.status = 'SUCCESS' GROUP BY p.paymentMethod ORDER BY COUNT(p) DESC")
    List<Object[]> getPaymentStatsByMethod();

    @Query("SELECT p.currency, COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "WHERE p.status = 'SUCCESS' GROUP BY p.currency ORDER BY SUM(p.amount) DESC")
    List<Object[]> getRevenueByCurrency();
}