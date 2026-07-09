package com.claimedge.repository;

import com.claimedge.entity.ComplianceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplianceReportRepository extends JpaRepository<ComplianceReport, Long> {
    List<ComplianceReport> findByStatus(String status);
    List<ComplianceReport> findByReportType(String reportType);
}
