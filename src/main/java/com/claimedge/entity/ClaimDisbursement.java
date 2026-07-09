package com.claimedge.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "claim_disbursement")
public class ClaimDisbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disbursementId;

    private Long claimId;
    private Double amount;
    private LocalDate disbursementDate;

    @Enumerated(EnumType.STRING)
    private DisbursementStatus status = DisbursementStatus.PENDING;

    public enum DisbursementStatus {
        PENDING, PROCESSED, FAILED
    }

    public Long getDisbursementId() { return disbursementId; }
    public void setDisbursementId(Long disbursementId) { this.disbursementId = disbursementId; }

    public Long getClaimId() { return claimId; }
    public void setClaimId(Long claimId) { this.claimId = claimId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getDisbursementDate() { return disbursementDate; }
    public void setDisbursementDate(LocalDate disbursementDate) { this.disbursementDate = disbursementDate; }

    public DisbursementStatus getStatus() { return status; }
    public void setStatus(DisbursementStatus status) { this.status = status; }
}
