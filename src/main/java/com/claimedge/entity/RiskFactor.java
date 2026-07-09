package com.claimedge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_factor")
public class RiskFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long factorId;

    private Long applicationId;
    private String factorType;
    private String factorValue;
    private Double weight;

    public Long getFactorId() { return factorId; }
    public void setFactorId(Long factorId) { this.factorId = factorId; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getFactorType() { return factorType; }
    public void setFactorType(String factorType) { this.factorType = factorType; }

    public String getFactorValue() { return factorValue; }
    public void setFactorValue(String factorValue) { this.factorValue = factorValue; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}
