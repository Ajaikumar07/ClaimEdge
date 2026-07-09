package com.claimedge.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "compliance_report")
public class ComplianceReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String reportType;
    private String scope;
    private String scopeType;
    private LocalDate generatedDate;
    private String status;
    private String generatedBy;
    private String approvedBy;
    private String remarks;

    @PrePersist
    public void prePersist() {
        if (this.generatedDate == null) this.generatedDate = LocalDate.now();
        if (this.status == null) this.status = "GENERATED";
        if (this.generatedBy == null) this.generatedBy = "SYSTEM";
    }

    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

    public String getScopeType() { return scopeType; }
    public void setScopeType(String scopeType) { this.scopeType = scopeType; }

    public LocalDate getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDate generatedDate) { this.generatedDate = generatedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(String generatedBy) { this.generatedBy = generatedBy; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
