package com.claimedge.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fraud_flag")
public class FraudFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flagId;

    private Long claimId;
    private String fraudType;

    @Column(nullable = false)
    private String severity = "LOW"; // LOW / MEDIUM / HIGH

    @Column(nullable = false)
    private String status = "OPEN"; // OPEN / INVESTIGATED / CLEARED

    private String description;
    private String detectedBy = "SYSTEM";
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "OPEN";
        if (this.detectedBy == null) this.detectedBy = "SYSTEM";
    }

    public Long getFlagId() { return flagId; }
    public void setFlagId(Long flagId) { this.flagId = flagId; }

    public Long getClaimId() { return claimId; }
    public void setClaimId(Long claimId) { this.claimId = claimId; }

    public String getFraudType() { return fraudType; }
    public void setFraudType(String fraudType) { this.fraudType = fraudType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDetectedBy() { return detectedBy; }
    public void setDetectedBy(String detectedBy) { this.detectedBy = detectedBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
