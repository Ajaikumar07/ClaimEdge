package com.claimedge.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    @Column(nullable = false)
    private Integer policyHolderId;

    @Column(nullable = false)
    private String productType; // Life / Health / Motor / Property

    private Double coverageAmount;
    private Double premium;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(nullable = false)
    private String status = "DRAFT"; // DRAFT / ACTIVE / LAPSED / CANCELLED

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public Integer getPolicyHolderId() { return policyHolderId; }
    public void setPolicyHolderId(Integer policyHolderId) { this.policyHolderId = policyHolderId; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public Double getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(Double coverageAmount) { this.coverageAmount = coverageAmount; }

    public Double getPremium() { return premium; }
    public void setPremium(Double premium) { this.premium = premium; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
