package com.claimedge.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "underwriting_application")
public class UnderwritingApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    private Long policyId;
    private Double riskScore;
    private Double premiumRecommended;
    private Long underwriterId;

    @Enumerated(EnumType.STRING)
    private Decision decision;

    private LocalDate decisionDate;

    public enum Decision {
        APPROVED, DECLINED, REFERRED
    }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public Double getRiskScore() { return riskScore; }
    public void setRiskScore(Double riskScore) { this.riskScore = riskScore; }

    public Double getPremiumRecommended() { return premiumRecommended; }
    public void setPremiumRecommended(Double premiumRecommended) { this.premiumRecommended = premiumRecommended; }

    public Long getUnderwriterId() { return underwriterId; }
    public void setUnderwriterId(Long underwriterId) { this.underwriterId = underwriterId; }

    public Decision getDecision() { return decision; }
    public void setDecision(Decision decision) { this.decision = decision; }

    public LocalDate getDecisionDate() { return decisionDate; }
    public void setDecisionDate(LocalDate decisionDate) { this.decisionDate = decisionDate; }
}
