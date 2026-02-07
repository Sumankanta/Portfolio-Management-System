package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}