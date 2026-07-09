package com.claimedge.service;

import com.claimedge.entity.ComplianceReport;
import com.claimedge.repository.ComplianceReportRepository;
import com.claimedge.repository.FraudFlagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceService {

    private final ComplianceReportRepository reportRepo;
    private final FraudFlagRepository fraudRepo;

    public ComplianceService(ComplianceReportRepository reportRepo, FraudFlagRepository fraudRepo) {
        this.reportRepo = reportRepo;
        this.fraudRepo = fraudRepo;
    }

    public ComplianceReport addReport(ComplianceReport report) {
        if (report.getReportType() == null) throw new RuntimeException("reportType is required");

        long highCount = fraudRepo.countBySeverity("HIGH");
        long total = fraudRepo.count();
        report.setRemarks("Total flags: " + total + " | High severity: " + highCount);

        if (highCount > 10) report.setStatus("UNDER_REVIEW");

        return reportRepo.save(report);
    }

    public List<ComplianceReport> getAllReports() { return reportRepo.findAll(); }

    public ComplianceReport getReportById(Long id) {
        return reportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found: " + id));
    }

    public List<ComplianceReport> getByStatus(String status) { return reportRepo.findByStatus(status); }

    public ComplianceReport approveReport(Long id, String approvedBy) {
        ComplianceReport report = getReportById(id);
        report.setStatus("APPROVED");
        report.setApprovedBy(approvedBy);
        return reportRepo.save(report);
    }

    public ComplianceReport rejectReport(Long id, String reason) {
        ComplianceReport report = getReportById(id);
        report.setStatus("REJECTED");
        report.setRemarks(reason);
        return reportRepo.save(report);
    }

    public void deleteReport(Long id) {
        if (!reportRepo.existsById(id)) throw new RuntimeException("Report not found: " + id);
        reportRepo.deleteById(id);
    }
}
