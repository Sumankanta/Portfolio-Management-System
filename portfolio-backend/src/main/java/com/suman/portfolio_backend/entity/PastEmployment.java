package com.suman.portfolio_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "past_employment", indexes = {
        @Index(name = "idx_employment_user", columnList = "user_id"),
        @Index(name = "idx_employment_dates", columnList = "start_date, end_date")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user"})
public class PastEmployment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"employments", "chatMessages", "userRoles", "payments", "uiSettings"})
    private User user;

    @NotBlank(message = "Company name is required")
    @Column(nullable = false)
    private String companyName;

    @NotBlank(message = "Job role is required")
    @Column(nullable = false)
    private String jobRole;

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean isCurrent = false;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @PrePersist
    @PreUpdate
    protected void validateDates() {
        if (isCurrent != null && isCurrent) {
            endDate = null;
        }
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }

    // Helper methods

    /**
     * Calculate duration of employment in months
     */
    public long getDurationInMonths() {
        if (startDate == null) {
            return 0;
        }
        LocalDate end = (isCurrent != null && isCurrent) ? LocalDate.now() : endDate;
        if (end == null) {
            end = LocalDate.now();
        }
        Period period = Period.between(startDate, end);
        return period.getYears() * 12L + period.getMonths();
    }

    /**
     * Get formatted duration string (e.g., "2 years 3 months")
     */
    public String getFormattedDuration() {
        if (startDate == null) {
            return "N/A";
        }
        LocalDate end = (isCurrent != null && isCurrent) ? LocalDate.now() : endDate;
        if (end == null) {
            end = LocalDate.now();
        }
        Period period = Period.between(startDate, end);

        StringBuilder duration = new StringBuilder();
        if (period.getYears() > 0) {
            duration.append(period.getYears()).append(" year");
            if (period.getYears() > 1) duration.append("s");
        }
        if (period.getMonths() > 0) {
            if (duration.length() > 0) duration.append(" ");
            duration.append(period.getMonths()).append(" month");
            if (period.getMonths() > 1) duration.append("s");
        }
        if (duration.length() == 0) {
            duration.append("Less than a month");
        }
        return duration.toString();
    }

    /**
     * Check if employment is currently active
     */
    public boolean isCurrentlyEmployed() {
        return Boolean.TRUE.equals(isCurrent);
    }
}