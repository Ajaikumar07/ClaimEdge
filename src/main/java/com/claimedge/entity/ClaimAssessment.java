package com.claimedge.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "claim_assessment")
public class ClaimAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessmentId;

    private Long claimId;
    private Long adjusterId;
    private Double approvedAmount;
    private String notes;
    private LocalDate assessmentDate;

    public Long getAssessmentId() { return assessmentId; }
    public void setAssessmentId(Long assessmentId) { this.assessmentId = assessmentId; }

    public Long getClaimId() { return claimId; }
    public void setClaimId(Long claimId) { this.claimId = claimId; }

    public Long getAdjusterId() { return adjusterId; }
    public void setAdjusterId(Long adjusterId) { this.adjusterId = adjusterId; }

    public Double getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(Double approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDate assessmentDate) { this.assessmentDate = assessmentDate; }
}
